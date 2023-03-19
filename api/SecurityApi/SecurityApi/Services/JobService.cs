using SecurityApi.Context;
using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public class JobService : IJobService
    {
        private readonly OnlabContext _context;
        public JobService(OnlabContext context)
        {
            _context = context;
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

        public Task<Job> Create(CreateJob job)
        {
            throw new NotImplementedException();
        }

        public Task<Job> Delete(int jobId)
        {
            throw new NotImplementedException();
        }

        public Job Get(int jobId)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Job> GetAll()
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Job> GetAllAwailableForPerson(int personId)
        {
            throw new NotImplementedException();
        }
    }
}
