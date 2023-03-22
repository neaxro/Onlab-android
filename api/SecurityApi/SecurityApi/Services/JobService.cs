using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos;
using SecurityApi.Enums;
using System.Data;

namespace SecurityApi.Services
{
    public class JobService : IJobService
    {
        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;
        private readonly Random _random;
        public JobService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
            _random = new Random();
        }

        public IEnumerable<Person> AllPersonInJob(int jobId)
        {
            throw new NotImplementedException();
        }

        public Task ChangePersonRole(int personId, int roleId)
        {
            throw new NotImplementedException();
        }

        public async Task<PersonJob> ConnectToJob(CreateConnection connection)
        {
            if(connection.WageId == DatabaseConstants.BROADCAST_MESSAGE_ID)
            {
                throw new DataException(String.Format("Invalid Wage Id({0})", connection.WageId));
            }

            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            // Job already has owner?
            if(connection.RoleId == DatabaseConstants.ROLE_OWNER_ID)
            {
                var alreadyExists = await _context.PeopleJobs
                    .FirstOrDefaultAsync(pj => pj.JobId == connection.JobId && pj.RoleId == DatabaseConstants.ROLE_OWNER_ID);

                if (alreadyExists != null)
                {
                    await tran.RollbackAsync();
                    throw new DataException("Job already has owner!");
                }
            }

            // She/He already connected?
            var duplicateConnection = await _context.PeopleJobs
                .FirstOrDefaultAsync(pj => pj.JobId == connection.JobId && pj.PeopleId == connection.PersonId);
            if (duplicateConnection != null)
            {
                await tran.RollbackAsync();
                throw new DataException("You are already connected to this job!");
            }

            var role = await _context.Roles.FirstOrDefaultAsync(r => r.Id == connection.RoleId);
            if (role == null)
            {
                await tran.RollbackAsync();
                throw new DataException("Role does not exist!");
            }

            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Id == connection.JobId);
            if(job == null)
            {
                await tran.RollbackAsync();
                throw new DataException("Job does not exist!");
            }

            var person = await _context.People.FirstOrDefaultAsync(pj => pj.Id == connection.PersonId);
            if (person == null)
            {
                await tran.RollbackAsync();
                throw new DataException("Person does not exist!");
            }

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == connection.WageId);
            if (wage == null)
            {
                await tran.RollbackAsync();
                throw new DataException("Wage does not exist!");
            }

            var newPeopleJob = new Model.PeopleJob()
            {
                People = person,
                Job = job,
                Role = role,
                Wage = wage
            };

            await _context.PeopleJobs.AddAsync(newPeopleJob);
            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            return newPeopleJob == null ? null : _converter.ToModel(newPeopleJob);
        }

        public async Task<Job> Create(CreateJob job)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var sameTitle = await _context.Jobs.FirstOrDefaultAsync(j => j.Title == job.Title);
            
            if(sameTitle != null)
            {
                await tran.RollbackAsync();
                throw new DataException(String.Format("Job with title \"{0}\" already exists! Job's title must be unique!", job.Title));
            }

            var owner = await _context.People.FirstOrDefaultAsync(p => p.Id == job.OwnerId);

            if(owner == null)
            {
                await tran.RollbackAsync();
                throw new DataException(String.Format("Person with ID({0}) not found! Owner is null", job.OwnerId));
            }

            var usedPins = await _context.Jobs.Select(j => j.Pin).ToListAsync();
            string pin = "RANDOMPIN";

            do
            {
                pin = GeneratePin();
            } while (usedPins.Contains(pin));

            var newJob = new Model.Job()
            {
                Pin = pin,
                Title = job.Title,
                Description = job.Description,
                People = owner
            };

            await _context.Jobs.AddAsync(newJob);
            await _context.SaveChangesAsync();

            // Insert default Wages
            Model.Wage broadcastWage = new Model.Wage()
            {
                Name = "Everybody",
                Price = 0,
                Job = newJob
            };
            Model.Wage defaultWage = new Model.Wage()
            {
                Name = "Default",
                Price = 1300,
                Job = newJob
            };

            await _context.Wages.AddAsync(broadcastWage);
            await _context.Wages.AddAsync(defaultWage);

            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            // Connect the user in PeopleJobs table
            CreateConnection cc = new CreateConnection(newJob.Id, owner.Id, DatabaseConstants.ROLE_OWNER_ID, DatabaseConstants.DEFAULT_WAGE_ID);
            await ConnectToJob(cc);

            return _converter.ToModel(newJob);
        }

        public Task<Job> Delete(int jobId)
        {
            throw new NotImplementedException();
        }

        public async Task<Job> Get(int jobId)
        {
            var job = await _context.Jobs
                .Include(j => j.People)
                .FirstOrDefaultAsync(j => j.Id == jobId);

            return job == null ? null : _converter.ToModel(job);
        }

        public IEnumerable<Job> GetAll()
        {
            var jobs = _context.Jobs
                .Include(j => j.People)
                .Select(_converter.ToModel)
                .ToList();

            return jobs;
        }

        public IEnumerable<Job> GetAllAwailableForPerson(int personId)
        {
            var jobs = _context.PeopleJobs
                .Include(pj => pj.Job)
                    .ThenInclude(j => j.People)
                .Include(pj => pj.People)
                .Include(pj => pj.Wage)
                .Where(pj => pj.PeopleId == personId)
                .Select(pj => pj.Job)
                .Select(_converter.ToModel)
                .ToList();

            return jobs;
        }

        public async Task<PersonJob> GetConnection(int connectionId)
        {
            var connection = await _context.PeopleJobs
                .Include(pj => pj.People)
                .Include(pj => pj.Job)
                .Include(pj => pj.Wage)
                .FirstOrDefaultAsync(pj => pj.Id == connectionId);

            return connection == null ? null : _converter.ToModel(connection);
        }

        private string GeneratePin(int length = 6)
        {
            // Numer of pins at default length: 36^6 = 2'176'782'336
            string simbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            string pin = "";

            for(int i = 0; i < length; ++i)
            {
                pin += simbols[_random.Next(0, simbols.Length)];
            }

            return pin;
        }
    }
}
