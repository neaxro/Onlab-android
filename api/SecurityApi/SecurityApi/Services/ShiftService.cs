using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Model;
using Shift = SecurityApi.Dtos.ShiftDtos.Shift;
using Person = SecurityApi.Dtos.PersonDtos.Person;
using Job = SecurityApi.Dtos.JobDtos.DetailJob;
using State = SecurityApi.Dtos.StatusDtos.Status;
using Wage = SecurityApi.Dtos.WageDtos.Wage;
using System.Data;
using SecurityApi.Converters;
using SecurityApi.Enums;
using SecurityApi.Dtos.ShiftDtos;

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
        public async Task<Shift> Create(CreateShift newShift)
        {
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var peopleJob = await _context.PeopleJobs
                .Include(pj => pj.Job)
                    .ThenInclude(j => j.People)
                .Include(pj => pj.People)
                .Include(pj => pj.Wage)
                .Include(pj => pj.Role)
                .FirstOrDefaultAsync(pj => pj.JobId == newShift.JobId && pj.PeopleId == newShift.PersonId);

            if(peopleJob == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Person does not exist in Job!");
            }

            var person = await _context.People
                .Include(p => p.Shifts)
                .FirstOrDefaultAsync(p => p.Id == newShift.PersonId);              

            var inProcessShift = person.Shifts
                .FirstOrDefault(s => s.JobId == newShift.JobId && s.EndTime == null);
            if (inProcessShift != null)
            {
                await tran.RollbackAsync();
                throw new Exception("You must finish your current shift!");
            }

            var itsWage = await _context.Wages
                .FirstOrDefaultAsync(w => w.Id == newShift.WageId && w.JobId == newShift.JobId);
            
            int broadcastWageId = DatabaseConstants.GetBroadcastWageID(newShift.JobId, _context);

            if(itsWage == null || itsWage.Id == broadcastWageId)
            {
                await tran.RollbackAsync();
                throw new Exception("Wage does not exist!");
            }

            var inProcessState = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.PROCESSING_STATUS_ID);

            string formatted = DateTime.Now.ToString("yyyy-MM-dd'T'HH:mm:ss.ffffff");
            DateTime now = DateTime.Parse(formatted);

            var shift = new Model.Shift()
            {
                StartTime = now,
                People = peopleJob.People,
                Job = peopleJob.Job,
                Wage = itsWage,
                Status = inProcessState
            };

            await _context.Shifts.AddAsync(shift);

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
                // Earne money can be null, good return value in case of bad/early call
                return null;
            }

            double pricePerHour = (double)shift.Wage.Price;

            double hours = (end - start).TotalHours;

            return (float)Math.Round((pricePerHour * hours), 2);
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

            if (shift == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Shift with ID({0}) does not exist!", shiftId));
            }

            var pendingStatus = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.PENDING_STATUS_ID);

            string formatted = DateTime.Now.ToString("yyyy-MM-dd'T'HH:mm:ss.ffffff");
            DateTime now = DateTime.Parse(formatted);

            shift.EndTime = now;
            shift.Status = pendingStatus;
            shift.EarnedMoney = CalculateEarnedMoney(shift);

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(shift);
        }

        public async Task<Shift> Delete(int shiftId)
        {
            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == shiftId);

            if (shift == null)
            {
                throw new Exception(String.Format("Shift with ID({0}) does not exist!", shiftId));
            }

            _context.Shifts.Remove(shift);
            await _context.SaveChangesAsync();

            return _converter.ToModel(shift);
        }

        public async Task<Shift> Get(int shiftId)
        {
            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == shiftId);

            if(shift == null)
            {
                throw new Exception(String.Format("Shift with ID({0}) does not exist!", shiftId));
            }

            return _converter.ToModel(shift);
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
            if(job == null)
            {
                throw new Exception(String.Format("Job with ID({0}) does not exist!", jobId));
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

            if (person == null)
            {
                throw new Exception(String.Format("Person with ID({0}) does not exist!", personId));
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

        public IEnumerable<Shift> GetAllForPersonInJob(int jobId, int personId)
        {
            var job = _context.Jobs.FirstOrDefault(job => job.Id == jobId);
            if(job == null)
            {
                throw new Exception(String.Format("Job with ID({0}) does not exist!", jobId));
            }
            
            var person = _context.People.FirstOrDefault(p => p.Id == personId);
            if (person == null)
            {
                throw new Exception(String.Format("Person with ID({0}) does not exist!", personId));
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
            if (job == null)
            {
                throw new Exception(String.Format("Job with ID({0}) does not exist!", jobId));
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

        public async Task<Shift> Update(int shiftId, UpdateShift newShift)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == shiftId);

            if (shift == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Shift does not exist!");
            }

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == newShift.WageId && w.JobId == shift.JobId);
            if (wage == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Wage with ID({0}) does not exist in Job!", newShift.WageId));
            }

            int broadcastWageId = DatabaseConstants.GetBroadcastWageID((int)wage.JobId, _context);
            if (wage.Id == broadcastWageId)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Wage with ID({0}) does not exist in Job!", newShift.WageId));
            }

            var state = await _context.States.FirstOrDefaultAsync(s => s.Id == newShift.StatusId);
            if(state == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("State with ID({0}) does not exist!", newShift.StatusId));
            }

            if(newShift.EndTime < newShift.StartTime)
            {
                await tran.RollbackAsync();
                throw new Exception("Invalid Start or End Time!");
            }

            shift.StartTime = newShift.StartTime;
            shift.EndTime = newShift.EndTime;
            shift.WageId = newShift.WageId;
            shift.StatusId = newShift.StatusId;

            await _context.SaveChangesAsync();

            // Because of the changes
            shift.EarnedMoney = CalculateEarnedMoney(shift);

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(shift);
        }

        public async Task<Shift> AcceptShift(int shiftId)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var acceptedStatus = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.ACCEPTED_STATUS_ID);

            if(acceptedStatus == null)
            {
                await tran.RollbackAsync();
                throw new DataException(String.Format("Status with ID({0}) doesnt exist!", DatabaseConstants.ACCEPTED_STATUS_ID));
            }

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == shiftId);

            if(shift == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Shift does not exist!");
            }

            if(shift.StatusId != DatabaseConstants.PENDING_STATUS_ID)
            {
                await tran.RollbackAsync();
                throw new Exception("Shift's status is not Pending!");
            }

            shift.Status = acceptedStatus;

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(shift);
        }

        public async Task<Shift> DenyShift(int id)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var denyStatus = await _context.States.FirstOrDefaultAsync(s => s.Id == DatabaseConstants.DENY_STATUS_ID);

            if (denyStatus == null)
            {
                await tran.RollbackAsync();
                throw new DataException(String.Format("Status with ID({0}) doesnt exist!", DatabaseConstants.DENY_STATUS_ID));
            }

            var shift = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.Id == id);

            if (shift == null)
            {
                await tran.RollbackAsync();
                throw new Exception("Shift does not exist!");
            }

            if (shift.StatusId != DatabaseConstants.PENDING_STATUS_ID)
            {
                await tran.RollbackAsync();
                throw new Exception("Shift's status is not Pending!");
            }

            shift.Status = denyStatus;

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(shift);
        }

        public async Task WageChangedUpdate(int wageId)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var shifts = await _context.Shifts
                .Include(s => s.Wage)
                .Where(s => s.WageId == wageId)
                .ToListAsync();

            foreach (var shift in shifts)
            {
                shift.EarnedMoney = CalculateEarnedMoney(shift);
            }

            await _context.SaveChangesAsync();
            await tran.CommitAsync();
        }

        public IEnumerable<Shift> GetAllInProgress(int jobId)
        {
            var inProgress = _context.Shifts
                .Where(s => s.JobId == jobId && (s.EndTime == null || s.Status.Id == DatabaseConstants.PROCESSING_STATUS_ID))
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .Select(_converter.ToModel)
                .ToList();

            return inProgress;
        }

        public async Task<Shift> GetInProgressForPersonInJob(int jobId, int personId)
        {
            var inProgress = await _context.Shifts
                .Include(s => s.People)
                .Include(s => s.Wage)
                .Include(s => s.Job)
                    .ThenInclude(j => j.People)
                .Include(s => s.Status)
                .FirstOrDefaultAsync(s => s.JobId == jobId && s.PeopleId == personId && (s.EndTime == null || s.Status.Id == DatabaseConstants.PROCESSING_STATUS_ID));

            if (inProgress == null)
                return null;

            return _converter.ToModel(inProgress);
        }
    }
}
