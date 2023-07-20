using Microsoft.Build.Framework;
using SecurityApi.Dtos;
using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PeopleJobDtos;
using SecurityApi.Dtos.RoleDtos;
using SecurityApi.Dtos.WageDtos;
using DetailJob = SecurityApi.Dtos.JobDtos.DetailJob;
using Person = SecurityApi.Dtos.PersonDtos.Person;

namespace SecurityApi.Services
{
    public interface IJobService
    {
        IEnumerable<DetailJob> GetAll();
        IEnumerable<DetailJob> GetAllAvailableForPerson(int personId);
        IEnumerable<PersonDetailed> AllPersonInJob(int jobId);
        Task<PersonDetailed> GetPersonDetailsInJob(int jobId, int personId);
        Task<String> SelectJob(SelectJob selectJob);
        Task<Job> Get(int jobId);
        Task<DetailJob> GetDetailed(int jobId);
        Task<Job> Create(CreateJob job);
        Task<DetailJob> Delete(int jobId);
        Task<PersonJob> ConnectToJob(string pin, int personId, int roleId);
        Task<PersonJob> ChangePersonRole(int jobId, ChangeRole change);
        Task<PersonJob> ChangePersonWage(int jobId, ChangeWage change);
        Task<PersonJob> GetConnection(int connectionId);
    }
}
