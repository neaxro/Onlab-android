using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos;
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

        public Task ConnectToJob(int jobId, int personId)
        {
            throw new NotImplementedException();
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

            await tran.CommitAsync();

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
            throw new NotImplementedException();
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
