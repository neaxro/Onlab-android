using SecurityApi.Dtos;
using Job = SecurityApi.Dtos.Job;
using Person = SecurityApi.Dtos.Person;

namespace SecurityApi.Services
{
    public interface IJobService
    {
        IEnumerable<Job> GetAll();
        IEnumerable<Job> GetAllAwailableForPerson(int personId);
        IEnumerable<Person> AllPersonInJob(int jobId);
        Task<Job> Get(int jobId);
        Task<Job> Create(CreateJob job);
        Task<Job> Delete(int jobId);
        Task<PersonJob> ConnectToJob(string pin, int personId, int roleId);
        Task<PersonJob> ChangePersonRole(int jobId, ChangeRole change);
        Task<PersonJob> GetConnection(int connectionId);
    }
}
