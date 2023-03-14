using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public interface IDashboardService
    {
        // Debug
        IEnumerable<Dashboard> ListAll();
        Task<Dashboard> GetById(int id);
        Task<Dashboard> ListForPerson(int personId);
        Task Insert(Dashboard dashboard);
        Task Update(int id);
    }
}
