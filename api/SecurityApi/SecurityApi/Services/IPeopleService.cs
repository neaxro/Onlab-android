using SecurityApi.Dtos;
using SecurityApi.Model;
using Person = SecurityApi.Dtos.Person;

namespace SecurityApi.Services
{
    public interface IPeopleService
    {
        // Debug
        IEnumerable<Person> GetAll();
        //IEnumerable<Person> GetAllOnJob(int jobId);
        // People/Admin
        //Task<IEnumerable<PersonDetailed>> GetAllOnJobDetailed();
        Task<Person> Insert(CreatePerson newPerson);
        Task<Person> FindById(int id);
        Task<Person> Update(int id, CreatePerson newData);
        Task<Person> DeleteById(int id);
        Task UploadImage(byte[] image);
    }
}
