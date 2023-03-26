using Microsoft.AspNetCore.Mvc.ModelBinding;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PeopleJobDtos;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Dtos.RoleDtos;
using SecurityApi.Dtos.WageDtos;
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
            var people = _context.PeopleJobs
                .Where(pj => pj.JobId == jobId)
                .Include(pj => pj.People)
                .Select(pj => pj.People)
                .Select(_converter.ToModel)
                .ToList();

            return people;
        }

        public async Task<PersonJob> ChangePersonRole(int jobId, ChangeRole change)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var peopleJobConnection = await _context.PeopleJobs
                .Include(pj => pj.Job)
                    .ThenInclude(j => j.People)
                .Include(pj => pj.People)
                .Include(pj => pj.Role)
                .Include(pj => pj.Wage)
                .FirstOrDefaultAsync(pj => pj.JobId == jobId && pj.PeopleId == change.PersonId);

            if(peopleJobConnection == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Person does not exist in this Job!");
            }

            if (peopleJobConnection.RoleId == DatabaseConstants.ROLE_OWNER_ID)
            {
                await tran.RollbackAsync();
                throw new Exception("Owner role cannot be changed!");
            }

            if (change.RoleId == DatabaseConstants.ROLE_OWNER_ID)
            {
                await tran.RollbackAsync();
                throw new Exception("Job must have only one Owner!");
            }
           

            var newRole = await _context.Roles.FirstOrDefaultAsync(r => r.Id == change.RoleId);
            if(newRole == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Role does not exist!");
            }

            peopleJobConnection.Role = newRole;

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(peopleJobConnection);
        }

        public async Task<PersonJob> ChangePersonWage(int jobId, ChangeWage change)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var peopleJobConnection = await _context.PeopleJobs
                .Include(pj => pj.Job)
                    .ThenInclude(j => j.People)
                .Include(pj => pj.People)
                .Include(pj => pj.Role)
                .Include(pj => pj.Wage)
                .FirstOrDefaultAsync(pj => pj.JobId == jobId && pj.PeopleId == change.PersonId);

            if (peopleJobConnection == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Person does not exist in this Job!");
            }

            int broadcastWageId = DatabaseConstants.GetBroadcastWageID(jobId, _context);
            var newWage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == change.WageId && w.JobId == jobId && w.Id != broadcastWageId);
            if (newWage == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Wage does not exist in Job!");
            }

            peopleJobConnection.Wage = newWage;

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(peopleJobConnection);
        }

        public async Task<PersonJob> ConnectToJob(string pin, int personId, int roleId)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Pin == pin);
            if (job == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Job does not exist!");
            }

            int broadcastWageId = DatabaseConstants.GetBroadcastWageID(job.Id, _context);
            int defaultWageId = DatabaseConstants.GetDefaultWageID(job.Id, _context);

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == defaultWageId && w.JobId == job.Id);
            if (wage == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Wage does not exist!");
            }        

            if(roleId == DatabaseConstants.ROLE_OWNER_ID)
            {
                var jobAlreadyHasOwner = await _context.PeopleJobs
                    .FirstOrDefaultAsync(pj => pj.JobId == job.Id && pj.RoleId == DatabaseConstants.ROLE_OWNER_ID);

                if (jobAlreadyHasOwner != null)
                {
                    await tran.RollbackAsync();
                    throw new Exception("Job already has owner!");
                }
            }

            var alreadyConnectedToJob = await _context.PeopleJobs
                .FirstOrDefaultAsync(pj => pj.JobId == job.Id && pj.PeopleId == personId);
            if (alreadyConnectedToJob != null)
            {
                await tran.RollbackAsync();
                throw new Exception("You are already connected to this job!");
            }

            var role = await _context.Roles.FirstOrDefaultAsync(r => r.Id == roleId);
            if (role == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Role does not exist!");
            }

            var person = await _context.People.FirstOrDefaultAsync(pj => pj.Id == personId);
            if (person == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Person does not exist!");
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

            return _converter.ToModel(newPeopleJob);
        }

        public async Task<Job> Create(CreateJob job)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var jobWithSameTitle = await _context.Jobs.FirstOrDefaultAsync(j => j.Title == job.Title);
            if(jobWithSameTitle != null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Job with title \"{0}\" already exists! Job's title must be unique!", job.Title));
            }

            var owner = await _context.People.FirstOrDefaultAsync(p => p.Id == job.OwnerId);
            if(owner == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Person with ID({0}) not found! Owner does not exist!", job.OwnerId));
            }

            var usedPins = await _context.Jobs
                .Select(j => j.Pin)
                .ToListAsync();

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
            await ConnectToJob(newJob.Pin.Trim(), owner.Id, DatabaseConstants.ROLE_OWNER_ID);

            return _converter.ToModel(newJob);
        }

        public async Task<Job> Delete(int jobId)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.Serializable);

            var job = await _context.Jobs
                .Include(j => j.People)
                .SingleOrDefaultAsync(j => j.Id == jobId);

            if(job == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("job with ID({0}) does not exist!", jobId));
            }

            var jobPositions = await _context.Positions.Where(p => p.JobId == jobId).ToListAsync();
            var jobPeopleJobs = await _context.PeopleJobs.Where(pj => pj.JobId== jobId).ToListAsync();
            var jobDashboards = await _context.Dashboards.Where(d => d.JobId == jobId).ToListAsync();
            var jobShifts = await _context.Shifts.Where(s => s.JobId == jobId).ToListAsync();
            var jobWages = await _context.Wages.Where(w => w.JobId == jobId).ToListAsync();

            try
            {
                _context.Positions.RemoveRange(jobPositions);
                _context.PeopleJobs.RemoveRange(jobPeopleJobs);
                _context.Dashboards.RemoveRange(jobDashboards);
                _context.Shifts.RemoveRange(jobShifts);
                _context.Wages.RemoveRange(jobWages);
                _context.Jobs.Remove(job);
            }
            catch (Exception ex)
            {
                await tran.RollbackAsync();
                throw new Exception(ex.Message);
            }

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(job);
        }

        public async Task<Job> Get(int jobId)
        {
            var job = await _context.Jobs
                .Include(j => j.People)
                .FirstOrDefaultAsync(j => j.Id == jobId);

            if(job == null)
            {
                throw new Exception(String.Format("Job with ID({0}) does not exist!", jobId));
            }

            return _converter.ToModel(job);
        }

        public IEnumerable<Job> GetAll()
        {
            var jobs = _context.Jobs
                .Include(j => j.People)
                .Select(_converter.ToModel)
                .ToList();

            return jobs;
        }

        public IEnumerable<Job> GetAllAvailableForPerson(int personId)
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

            if(connection == null)
            {
                throw new Exception(String.Format("Connection between Job and Person does not exist! Connection ID({0})", connectionId));
            }

            return _converter.ToModel(connection);
        }

        private string GeneratePin(int length = 6)
        {
            // Numer of pins at default length: 36^6 = 2'176'782'336
            // Chance for same pins generated: 1/36^6 ~= 4.6 * 10^(-10)
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
