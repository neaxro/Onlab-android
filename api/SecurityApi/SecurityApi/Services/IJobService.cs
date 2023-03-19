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
        Job Get(int jobId);
        Task<Job> Create(CreateJob job);
        Task<Job> Delete(int jobId);
        Task ConnectToJob(int jobId, int personId);
        Task ChangePersonRole(int personId, int roleId);
    }
}
