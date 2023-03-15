using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public interface IWageService
    {
        IEnumerable<Wage> GetAll();
        IEnumerable<Wage> GetWages();
        Task<Wage> GetById(int id);
        Task<Wage> Delete(int id);
        Task<Wage> Update(int id, CreateWage newContent);
        Task<Wage> Create(CreateWage newWage);
    }
}
