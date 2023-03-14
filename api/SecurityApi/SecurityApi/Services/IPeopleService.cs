using SecurityApi.Dtos;
using SecurityApi.Model;

namespace SecurityApi.Services
{
    public interface IPeopleService
    {
        IEnumerable<Person> GetAll();
        Task<Person> Insert(CreatePerson newPerson);
        Task<Person> FindById(int id);
    }
}
