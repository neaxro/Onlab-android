using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos.WageDtos;
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
            if(newWage.Price <= 0)
            {
                throw new Exception("Invalid price value for Wage!");
            }

            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Id == newWage.JobId);
            if (job == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Job with ID({0}) does not exist!", newWage.JobId));
            }

            var result = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Name== newWage.Name && w.JobId == newWage.JobId);

            if(result != null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Wage with name \"{0}\" already exists!", newWage.Name));
            }

            Model.Wage wage = new Model.Wage()
            {
                Name = newWage.Name,
                Price = newWage.Price,
                Job = job
            };

            await _context.Wages.AddAsync(wage);

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(wage);
        }

        public async Task<Wage> Delete(int wageId)
        {
            var wage = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Id == wageId);

            if (wage == null)
            {
                throw new Exception("Wage does not exist!");
            }

            int broadcastWageId = DatabaseConstants.GetBroadcastWageID((int)wage.JobId, _context);
            int defaultWageId = DatabaseConstants.GetDefaultWageID((int)wage.JobId, _context);

            if(wageId == broadcastWageId || wageId == defaultWageId)
            {
                throw new Exception("You do not have permission for delete this Wage!");
            }
            
            _context.Wages.Remove(wage);
            await _context.SaveChangesAsync();

            return _converter.ToModel(wage);
        }

        public async Task<Wage> GetById(int wageId)
        {
            var wage = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Id == wageId);

            if (wage == null)
            {
                throw new Exception(String.Format("Wage with ID(0}) does not exist!", wageId));
            }

            return _converter.ToModel(wage);
        }

        public IEnumerable<Wage> GetAll()
        {
            var wages = _context.Wages
                .Include(w => w.Job)
                .Select(_converter.ToModel).ToList();
            return wages;
        }

        public async Task<Wage> Update(int wageId, UpdateWage newWage)
        {
            if (newWage.Price <= 0)
            {
                throw new Exception("Invalid price value for Wage!");
            }

            var wage = await _context.Wages
                .Include(w => w.Job)
                .FirstOrDefaultAsync(w => w.Id == wageId);

            if (wage == null)
            {
                throw new Exception(String.Format("Wage with ID({0}) does not exist!", wageId));
            }
            
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

            return _converter.ToModel(wage);
        }

        public async Task<IEnumerable<Wage>> GetWagesInJob(int jobId)
        {
            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Id == jobId);

            if(job == null)
            {
                throw new Exception(String.Format("Job with ID({0}) does not exist!", jobId));
            }

            int broadcastWageId = DatabaseConstants.GetBroadcastWageID(jobId, _context);

            var wages = _context.Wages
                .Where(w => w.Id > broadcastWageId && w.JobId == jobId)
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
                throw new Exception(String.Format("Job with ID({0}) does not exist!", jobId));
            }

            var categories = _context.Wages
                .Where(w => w.JobId == jobId)
                .Select(_converter.WageToMessageCategory)
                .ToList();

            return categories;
        }
    }
}
