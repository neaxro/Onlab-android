using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Model;
using Person = SecurityApi.Dtos.PersonDtos.Person;

namespace SecurityApi.Services
{
    public interface IPeopleService
    {
        IEnumerable<Person> GetAll();
        Task<Person> Login(LoginPerson loginInformation);
        Task<Person> Register(CreatePerson newPerson);
        Task<Person> Get(int personId);
        Task<Person> Update(int personId, CreatePerson newData);
        Task<Person> Delete(int personId);
        Task<Person> UploadImage(int personId, IFormFile image);
        Task<Person> RemoveImage(int personId);
    }
}
