using SecurityApi.Context;
using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public class WageService : IWageService
    {
        private readonly OnlabContext _content;
        public WageService(OnlabContext content)
        {
            _content = content;
        }

        public Task<Wage> Create(CreateWage newWage)
        {
            throw new NotImplementedException();
        }

        public Task<Wage> Delete(int id)
        {
            throw new NotImplementedException();
        }

        public Task<Wage> GetById(int id)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Wage>> ListAll()
        {
            throw new NotImplementedException();
        }

        public Task<Wage> Update(int id, CreateWage newContent)
        {
            throw new NotImplementedException();
        }
    }
}
