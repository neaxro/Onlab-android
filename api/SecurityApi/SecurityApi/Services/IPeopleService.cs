using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Model;
using Person = SecurityApi.Dtos.PersonDtos.Person;

namespace SecurityApi.Services
{
    public interface IPeopleService
    {
        IEnumerable<Person> GetAll();
        Task<Person> Insert(CreatePerson newPerson);
        Task<Person> FindById(int id);
        Task<Person> Update(int id, CreatePerson newData);
        Task<Person> DeleteById(int id);
        Task<Person> UploadImage(int id, IFormFile image);
        Task<Person> RemoveImage(int id);
    }
}
