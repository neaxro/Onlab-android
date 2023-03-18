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

namespace SecurityApi.Services
{
    public class ShiftService : IShiftService
    {
        private readonly OnlabContext _context;
        private const int PENDING_STATUS_ID = 1;
        private const int PROCESSING_STATUS_ID = 2;
        private const int BROADCAST_WAGE_ID = 1;
        public ShiftService(OnlabContext context)
        {
            _context = context;
        }
        public async Task<Shift> Create(int id, CreateShift newShift)
        {
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var itsPerson = await _context.People
                .Include(p => p.Shifts)
                .FirstOrDefaultAsync(p => p.Id == id);

            var inProcessShift = itsPerson.Shifts.FirstOrDefault(s => s.EndTime == null);

            if(inProcessShift != null)
            {
                throw new Exception("Not finished shift!");
            }

            if( itsPerson == null )
            {
                throw new Exception("User doesnt exist!");
            }

            var itsJob = await _context.Jobs.Include(j => j.People).FirstOrDefaultAsync(j => j.Id == newShift.JobId);

            if( itsJob == null )
            {
                throw new Exception("Job doesnt exist!");
            }

            var itsWage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == newShift.WageId);

            if(itsWage == null || itsWage.Id == BROADCAST_WAGE_ID)
            {
                throw new Exception("Wage doesnt exist!");
            }

            var inProcessState = await _context.States.FirstOrDefaultAsync(s => s.Id == PROCESSING_STATUS_ID);

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

            return ToModel(shift);
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

        public async Task<Shift> Finish(int personId)
        {
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s =>
                s.PeopleId == personId && s.EndTime == null);

            var pendingStatus = await _context.States.FirstOrDefaultAsync(s => s.Id == PENDING_STATUS_ID);

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

            return ToModel(shift);
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

            return ToModel(shift);
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
            return result == null ? null : ToModel(result);
        }

        public IEnumerable<Shift> GetAll()
        {
            var shifts = _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(ToModel)
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
                .Select(ToModel)
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
                .Select(ToModel)
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
                .Select(ToModel)
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
                .Where(s => s.JobId == jobId && s.StatusId == PENDING_STATUS_ID)
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(ToModel)
                .ToList();
            return shifts;
        }

        public async Task<Shift> Update(int id, UpdateShift newShift)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == newShift.WageId);
            var state = await _context.States.FirstOrDefaultAsync(s => s.Id == newShift.StatusId);

            if(wage == null || wage.Id == BROADCAST_WAGE_ID)
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

                // Because of the changes
                shift.EarnedMoney = CalculateEarnedMoney(shift);

                await _context.SaveChangesAsync();
                await tran.CommitAsync();
            }
            else
            {
                await tran.RollbackAsync();
            }

            return shift == null ? null : ToModel(shift);
        }

        public async Task<Shift> AcceptShift(int id)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            const int ACCEPT_STATUS_ID = 4;

            var status = await _context.States.FirstOrDefaultAsync(s => s.Id == ACCEPT_STATUS_ID);

            if(status == null)
            {
                throw new DataException(String.Format("Status with ID({0}) doesnt exist!", ACCEPT_STATUS_ID));
            }

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);

            if(shift != null && shift.StatusId == PENDING_STATUS_ID)
            {
                shift.Status = status;

                await _context.SaveChangesAsync();
                await tran.CommitAsync();
            }
            else if(shift != null)
            {
                throw new DataException("Shift's status is not Pending!");
            }

            return shift == null ? null : ToModel(shift);
        }

        public async Task<Shift> DenyShift(int id)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            const int DENY_STATUS_ID = 5;

            var status = await _context.States.FirstOrDefaultAsync(s => s.Id == DENY_STATUS_ID);

            if (status == null)
            {
                throw new DataException(String.Format("Status with ID({0}) doesnt exist!", DENY_STATUS_ID));
            }

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);

            if (shift != null && shift.StatusId == PENDING_STATUS_ID)
            {
                shift.Status = status;

                await _context.SaveChangesAsync();
                await tran.CommitAsync();
            }
            else if (shift != null)
            {
                throw new DataException("Shift's status is not Pending!");
            }

            return shift == null ? null : ToModel(shift);
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

        private Shift ToModel(Model.Shift shift)
        {
            Person p = new Person(
                shift.People.Id,
                shift.People.Name,
                shift.People.Username,
                shift.People.Nickname,
                shift.People.Email,
                shift.People.ProfilePicture
                );

            Person owner = new Person(
                shift.Job.People.Id,
                shift.Job.People.Name,
                shift.Job.People.Username,
                shift.Job.People.Nickname,
                shift.Job.People.Email,
                null
                );

            Job j = new Job(
                shift.Job.Id,
                shift.Job.Title,
                shift.Job.Description,
                owner
                );

            State s = new State(
                shift.Status.Id,
                shift.Status.Title,
                shift.Status.Description
                );

            Wage w = new Wage(
                shift.Wage.Id,
                shift.Wage.Name,
                shift.Wage.Price
                );

            return new Shift(shift.Id, shift.StartTime, shift.EndTime, shift.EarnedMoney, p, j, s, w);
        }
    }
}
