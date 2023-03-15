using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using System.Data;

namespace SecurityApi.Services
{
    public class WageService : IWageService
    {
        private readonly OnlabContext _context;
        public WageService(OnlabContext context)
        {
            _context = context;
        }

        public async Task<Wage> Create(CreateWage newWage)
        {
            // It doesnt make sense...
            if(newWage.Price <= 0)
            {
                throw new ArgumentOutOfRangeException(nameof(newWage.Price));
            }

            Model.Wage wage = null;
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var result = await _context.Wages.FirstOrDefaultAsync(w => w.Name== newWage.Name);

            if(result == null)
            {
                wage = new Model.Wage()
                {
                    Name = newWage.Name,
                    Price = newWage.Price
                };

                await _context.Wages.AddAsync(wage);
            }

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return wage == null ? null : ToModel(wage);
        }

        public async Task<Wage> Delete(int id)
        {
            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == id);
            
            if(wage != null)
            {
                _context.Wages.Remove(wage);
                await _context.SaveChangesAsync();
            }

            return wage == null ? null : ToModel(wage);
        }

        public async Task<Wage> GetById(int id)
        {
            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == id);
            return wage == null ? null : ToModel(wage);
        }

        public IEnumerable<Wage> GetAll()
        {
            var wages = _context.Wages.Select(ToModel).ToList();
            return wages;
        }

        public async Task<Wage> Update(int id, CreateWage newWage)
        {
            // It doesnt make sense...
            if (newWage.Price <= 0)
            {
                throw new ArgumentOutOfRangeException(nameof(newWage.Price));
            }

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == id);
            
            if(wage != null)
            {
                wage.Name = newWage.Name;
                wage.Price = newWage.Price;

                await _context.SaveChangesAsync();
            }

            return wage == null ? null : ToModel(wage);
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
