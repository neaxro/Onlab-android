using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public class WageService : IWageService
    {
        private readonly OnlabContext _context;
        public WageService(OnlabContext context)
        {
            _context = context;
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

        public IEnumerable<Wage> GetAll()
        {
            var wages = _context.Wages.Select(ToModel).ToList();
            return wages;
        }

        public Task<Wage> Update(int id, CreateWage newContent)
        {
            throw new NotImplementedException();
        }

        private Wage ToModel(Model.Wage wage)
        {
            return new Wage(wage.Id, wage.Name, wage.Price);
        }

        public IEnumerable<Wage> GetWages()
        {
            var wages = _context.Wages
                .Where(w => w.Id > 1)
                .Select(ToModel)
                .ToList();
            return wages;
        }
    }
}
