using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos;
using SecurityApi.Enums;
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

            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Id == newWage.JobId);
            if (job == null)
            {
                throw new DataException(String.Format("Job with ID({0}) does not exist!", newWage.JobId));
            }

            var result = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Name== newWage.Name && w.JobId == newWage.JobId);

            if(result != null)
            {
                throw new DataException(String.Format("Wage with name \"{0}\" already exists!", newWage.Name));
            }

            wage = new Model.Wage()
            {
                Name = newWage.Name,
                Price = newWage.Price,
                Job = job
            };

            await _context.Wages.AddAsync(wage);

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return wage == null ? null : _converter.ToModel(wage);
        }

        public async Task<Wage> Delete(int id)
        {
            if(id == DatabaseConstants.BROADCAST_MESSAGE_ID || id == DatabaseConstants.DEFAULT_WAGE_ID)
            {
                throw new Exception("You do not have permission for delete this Wage!");
            }

            var wage = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Id == id);
            
            if(wage != null)
            {
                _context.Wages.Remove(wage);
                await _context.SaveChangesAsync();
            }

            return wage == null ? null : _converter.ToModel(wage);
        }

        public async Task<Wage> GetById(int id)
        {
            var wage = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Id == id);
            return wage == null ? null : _converter.ToModel(wage);
        }

        public IEnumerable<Wage> GetAll()
        {
            var wages = _context.Wages
                .Include(w => w.Job)
                .Select(_converter.ToModel).ToList();
            return wages;
        }

        public async Task<Wage> Update(int id, CreateWage newWage)
        {
            // It doesnt make sense...
            if (newWage.Price <= 0)
            {
                throw new ArgumentOutOfRangeException(nameof(newWage.Price));
            }

            var job = await _context.Jobs.FirstOrDefaultAsync(job => job.Id == newWage.JobId);

            if (job == null)
            {
                throw new DataException(String.Format("Job with ID({0}) does not exist!", newWage.JobId));
            }

            var wage = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Id == id);
            
            if(wage != null)
            {
                bool priceChange = false;
                if(wage.Price != newWage.Price)
                {
                    priceChange = true;
                }

                wage.Name = newWage.Name;
                wage.Price = newWage.Price;
                wage.Job = job;

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
                .Include(w => w.Job)
                .Select(_converter.ToModel)
                .ToList();
            return wages;
        }

        public async Task<IEnumerable<Wage>> GetWagesInJob(int jobId)
        {
            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Id == jobId);

            if(job == null)
            {
                throw new DataException(String.Format("Job with ID({0}) does not exist!", jobId));
            }

            var wages = _context.Wages
                .Where(w => w.Id > 1 && w.JobId == jobId)
                .Include(w => w.Job)
                .Select(_converter.ToModel)
                .ToList();

            return wages;
        }

        public async Task<IEnumerable<MessageCategory>> GetMessageCategories(int jobId)
        {
            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Id == jobId);

            if (job == null)
            {
                throw new DataException(String.Format("Job with ID({0}) does not exist!", jobId));
            }

            var categories = _context.Wages
                .Where(w => w.JobId == jobId)
                .Select(_converter.WageToMessageCategory)
                .ToList();

            return categories;
        }
    }
}
