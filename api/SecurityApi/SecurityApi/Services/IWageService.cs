using SecurityApi.Dtos.WageDtos;

namespace SecurityApi.Services
{
    public interface IWageService
    {
        IEnumerable<Wage> GetAll();
        Task<IEnumerable<Wage>> GetWagesInJob(int jobId);
        Task<IEnumerable<MessageCategory>> GetMessageCategories(int jobId);
        Task<Wage> GetById(int wageId);
        Task<Wage> Delete(int wageId);
        Task<Wage> Update(int wageId, UpdateWage newContent);
        Task<Wage> Create(CreateWage newWage);
    }
}
