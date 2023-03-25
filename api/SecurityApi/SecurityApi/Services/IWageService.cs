using SecurityApi.Dtos.WageDtos;

namespace SecurityApi.Services
{
    public interface IWageService
    {
        IEnumerable<Wage> GetAll();
        IEnumerable<Wage> GetWages();
        Task<IEnumerable<Wage>> GetWagesInJob(int jobId);
        Task<IEnumerable<MessageCategory>> GetMessageCategories(int jobId);
        Task<Wage> GetById(int id);
        Task<Wage> Delete(int id);
        Task<Wage> Update(int id, UpdateWage newContent);
        Task<Wage> Create(CreateWage newWage);
    }
}
