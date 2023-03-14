using SecurityApi.Dtos;
using SecurityApi.Model;

namespace SecurityApi.Services
{
    public interface IPeopleService
    {
        // Debug
        IEnumerable<Person> GetAll();
        
        // Register
        Task<Person> Insert(CreatePerson newPerson);
        
        Task<Person> FindById(int id);

        // Edit user data
        Task<Person> Update(CreatePerson newData);
    }
}
