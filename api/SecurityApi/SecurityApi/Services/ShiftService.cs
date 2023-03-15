using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using Shift = SecurityApi.Dtos.Shift;
using Person = SecurityApi.Dtos.Person;
using Job = SecurityApi.Dtos.Job;
using State = SecurityApi.Dtos.State;
using Wage = SecurityApi.Dtos.Wage;
using Azure.Identity;

namespace SecurityApi.Services
{
    public class ShiftService : IShiftService
    {
        private readonly OnlabContext _context;
        public ShiftService(OnlabContext context)
        {
            _context = context;
        }
        public Task<Shift> Create(CreateShift newShift)
        {
            throw new NotImplementedException();
        }

        public Task<Shift> Delete(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Shift> Get(int id)
        {
            throw new NotImplementedException();
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
            const int PENDING_STATUS_ID = 1;

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

        public Task<Shift> Update(UpdateShift newShift)
        {
            throw new NotImplementedException();
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
