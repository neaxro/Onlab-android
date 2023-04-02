using Microsoft.Build.Framework;
using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PeopleJobDtos;
using SecurityApi.Dtos.RoleDtos;
using SecurityApi.Dtos.WageDtos;
using Job = SecurityApi.Dtos.JobDtos.Job;
using Person = SecurityApi.Dtos.PersonDtos.Person;

namespace SecurityApi.Services
{
    public interface IJobService
    {
        IEnumerable<Job> GetAll();
        IEnumerable<Job> GetAllAvailableForPerson(int personId);
        IEnumerable<Person> AllPersonInJob(int jobId);
        Task<String> SelectJob(SelectJob selectJob);
        Task<Job> Get(int jobId);
        Task<Job> Create(CreateJob job);
        Task<Job> Delete(int jobId);
        Task<PersonJob> ConnectToJob(string pin, int personId, int roleId);
        Task<PersonJob> ChangePersonRole(int jobId, ChangeRole change);
        Task<PersonJob> ChangePersonWage(int jobId, ChangeWage change);
        Task<PersonJob> GetConnection(int connectionId);
    }
}
