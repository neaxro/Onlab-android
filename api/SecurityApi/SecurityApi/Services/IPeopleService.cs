using SecurityApi.Dtos;
using SecurityApi.Model;

namespace SecurityApi.Services
{
    public interface IPeopleService
    {
        Person Insert(CreatePerson newPerson);

        Person FindById(int id);
    }
}
