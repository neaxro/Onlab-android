using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using Shift = SecurityApi.Dtos.Shift;
using Person = SecurityApi.Dtos.Person;
using Job = SecurityApi.Dtos.Job;
using State = SecurityApi.Dtos.State;
using Wage = SecurityApi.Dtos.Wage;
using System.Data;
using SecurityApi.Converters;
using SecurityApi.Enums;

namespace SecurityApi.Services
{
    public class ShiftService : IShiftService
    {
        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;
        public ShiftService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
        }
        public async Task<Shift> Create(int personId, CreateShift newShift)
        {
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var itsPerson = await _context.People
                .Include(p => p.Shifts)
                .FirstOrDefaultAsync(p => p.Id == personId);

            var inProcessShift = itsPerson.Shifts.FirstOrDefault(s => s.EndTime == null);

            if(inProcessShift != null)
            {
                throw new Exception("Not finished shift!");
            }

            if( itsPerson == null )
            {
                throw new Exception("User doesnt exist!");
            }

            var itsJob = await _context.Jobs
                .Include(j => j.People)
                .FirstOrDefaultAsync(j => j.Id == newShift.JobId);

            if( itsJob == null )
            {
                throw new Exception("Job doesnt exist!");
            }

            var itsWage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == newShift.WageId);
            int broadcastWageId = DatabaseConstants.GetBroadcastWageID(newShift.JobId, _context);

            if(itsWage == null || itsWage.Id == broadcastWageId)
            {
                throw new Exception("Wage doesnt exist!");
            }

            var inProcessState = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.PROCESSING_STATUS_ID);

            var shift = new Model.Shift()
            {
                StartTime = DateTime.Now,
                People = itsPerson,
                Job = itsJob,
                Wage = itsWage,
                Status = inProcessState
            };

            _context.Shifts.Add(shift);

            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            return _converter.ToModel(shift);
        }

        private float? CalculateEarnedMoney(Model.Shift shift)
        {
            DateTime start = (DateTime)shift.StartTime;
            DateTime end = (DateTime)shift.EndTime;

            if(end == null)
            {
                return null;
            }

            double wagePrice = (double)shift.Wage.Price;

            double hours = (end - start).TotalHours;

            return (float)(wagePrice * Math.Round(hours, 1));
        }

        public async Task<Shift> Finish(int shiftId)
        {
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s =>
                s.Id == shiftId && s.EndTime == null);

            var pendingStatus = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.PENDING_STATUS_ID);

            // TODO: Esetleg leellenorizni h van-e ilyen ember, van-e befejezetlen szolgalata?
            if (shift == null)
            {
                return null;
            }

            shift.EndTime = DateTime.Now;

            shift.Status = pendingStatus;

            float? earnedMoney = CalculateEarnedMoney(shift);

            shift.EarnedMoney = earnedMoney;

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(shift);
        }

        public async Task<Shift> Delete(int id)
        {
            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);

            if (shift == null)
                return null;

            _context.Shifts.Remove(shift);
            await _context.SaveChangesAsync();

            return _converter.ToModel(shift);
        }

        public async Task<Shift> Get(int id)
        {
            var result = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);
            return result == null ? null : _converter.ToModel(result);
        }

        public IEnumerable<Shift> GetAll()
        {
            var shifts = _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(_converter.ToModel)
                .ToList();
            return shifts;
        }

        public IEnumerable<Shift> GetAllForJob(int jobId)
        {
            var job = _context.Jobs.FirstOrDefault(job => job.Id == jobId);
            
            // Job doesnt exist
            if(job == null)
            {
                return null;
            }

            var shifts = _context.Shifts
                .Where(s => s.JobId == jobId)
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(_converter.ToModel)
                .ToList();
            return shifts;
        }

        public IEnumerable<Shift> GetAllForPerson(int personId)
        {
            var person = _context.People.FirstOrDefault(p => p.Id == personId);

            // Person doesnt exist
            if (person == null)
            {
                return null;
            }

            var shifts = _context.Shifts
                .Where(s => s.PeopleId == personId)
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(_converter.ToModel)
                .ToList();
            return shifts;
        }

        public IEnumerable<Shift> GetAllForPersonInJob(int personId, int jobId)
        {
            var job = _context.Jobs.FirstOrDefault(job => job.Id == jobId);
            var person = _context.People.FirstOrDefault(p => p.Id == personId);

            // Job or Person doesnt exist
            if (job == null || person == null)
            {
                return null;
            }

            var shifts = _context.Shifts
                .Where(s => s.JobId == jobId && s.PeopleId == personId)
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(_converter.ToModel)
                .ToList();
            return shifts;
        }

        public async Task<IEnumerable<Shift>> GetAllPendingInJob(int jobId)
        {
            var job = await _context.Jobs.FirstOrDefaultAsync(job => job.Id == jobId);

            // Job doesnt exist
            if (job == null)
            {
                return null;
            }

            var shifts = _context.Shifts
                .Where(s => s.JobId == jobId && s.StatusId == DatabaseConstants.PENDING_STATUS_ID)
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(_converter.ToModel)
                .ToList();
            return shifts;
        }

        public async Task<Shift> Update(int id, UpdateShift newShift)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == newShift.WageId);
            var state = await _context.States.FirstOrDefaultAsync(s => s.Id == newShift.StatusId);

            int broadcastWageId = DatabaseConstants.GetBroadcastWageID((int)wage.JobId, _context);

            if(wage == null || wage.Id == broadcastWageId)
            {
                throw new ArgumentException("New Wage doesnt exist!");
            }
            if(state == null)
            {
                throw new ArgumentException("New State doesnt exist!");
            }
            if(newShift.EndTime < newShift.StartTime)
            {
                throw new ArgumentException("Invalid Start or End Time!");
            }


            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);

            if(shift != null)
            {
                shift.StartTime = newShift.StartTime;
                shift.EndTime = newShift.EndTime;
                shift.WageId = newShift.WageId;
                shift.StatusId = newShift.StatusId;

                await _context.SaveChangesAsync();

                // Because of the changes
                float? money = CalculateEarnedMoney(shift);
                shift.EarnedMoney = money;

                await _context.SaveChangesAsync();
                await tran.CommitAsync();
            }
            else
            {
                await tran.RollbackAsync();
            }

            return shift == null ? null : _converter.ToModel(shift);
        }

        public async Task<Shift> AcceptShift(int id)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var status = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.ACCEPTED_STATUS_ID);

            if(status == null)
            {
                throw new DataException(String.Format("Status with ID({0}) doesnt exist!", DatabaseConstants.ACCEPTED_STATUS_ID));
            }

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);

            if(shift != null && shift.StatusId == DatabaseConstants.PENDING_STATUS_ID)
            {
                shift.Status = status;

                await _context.SaveChangesAsync();
                await tran.CommitAsync();
            }
            else if(shift != null)
            {
                throw new DataException("Shift's status is not Pending!");
            }

            return shift == null ? null : _converter.ToModel(shift);
        }

        public async Task<Shift> DenyShift(int id)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var status = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.DENY_STATUS_ID);

            if (status == null)
            {
                throw new DataException(String.Format("Status with ID({0}) doesnt exist!", DatabaseConstants.DENY_STATUS_ID));
            }

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);

            if (shift != null && shift.StatusId == DatabaseConstants.PENDING_STATUS_ID)
            {
                shift.Status = status;

                await _context.SaveChangesAsync();
                await tran.CommitAsync();
            }
            else if (shift != null)
            {
                throw new DataException("Shift's status is not Pending!");
            }

            return shift == null ? null : _converter.ToModel(shift);
        }

        public async Task WageChangedUpdate(int wageId)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var shifts = await _context.Shifts
                .Include(s => s.Wage)
                .Where(s => s.WageId == wageId)
                .ToListAsync();

            foreach ( var shift in shifts)
            {
                shift.EarnedMoney = CalculateEarnedMoney(shift);
            }

            await _context.SaveChangesAsync();
            await tran.CommitAsync();
        }

        public IEnumerable<Shift> GetAllInProgress(int jobId)
        {
            var inProgress = _context.Shifts
                .Where(s => s.EndTime == null || s.Status.Id == DatabaseConstants.PROCESSING_STATUS_ID)
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(_converter.ToModel)
                .ToList();

            return inProgress;
        }
    }
}
