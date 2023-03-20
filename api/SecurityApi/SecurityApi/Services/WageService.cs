using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos;
using System.Data;

namespace SecurityApi.Services
{
    public class WageService : IWageService
    {
        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;
        private IShiftService _shiftService;
        public WageService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
            _shiftService = new ShiftService(context);
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

            return wage == null ? null : _converter.ToModel(wage);
        }

        public async Task<Wage> Delete(int id)
        {
            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == id);
            
            if(wage != null)
            {
                _context.Wages.Remove(wage);
                await _context.SaveChangesAsync();
            }

            return wage == null ? null : _converter.ToModel(wage);
        }

        public async Task<Wage> GetById(int id)
        {
            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == id);
            return wage == null ? null : _converter.ToModel(wage);
        }

        public IEnumerable<Wage> GetAll()
        {
            var wages = _context.Wages.Select(_converter.ToModel).ToList();
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
                bool priceChange = false;
                if(wage.Price != newWage.Price)
                {
                    priceChange = true;
                }

                wage.Name = newWage.Name;
                wage.Price = newWage.Price;

                await _context.SaveChangesAsync();

                // Update all shifts prices where needed
                if (priceChange)
                {
                    await _shiftService.WageChangedUpdate(wage.Id);
                }
            }

            return wage == null ? null : _converter.ToModel(wage);
        }

        public IEnumerable<Wage> GetWages()
        {
            var wages = _context.Wages
                .Where(w => w.Id > 1)
                .Select(_converter.ToModel)
                .ToList();
            return wages;
        }
    }
}
