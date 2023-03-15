using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public interface IDashboardService
    {
        // Debug
        IEnumerable<Dashboard> ListAll();
        Task<Dashboard> GetById(int id);
        IEnumerable<Dashboard> ListForPersonByCategoryID(int categoryId);
        Task<IEnumerable<Dashboard>> ListForPersonByPersonID(int personId);
        Task<Dashboard> Insert(CreateDashboard dashboard);
        Task<Dashboard> Update(int id);
    }
}
