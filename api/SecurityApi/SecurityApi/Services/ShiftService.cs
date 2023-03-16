﻿using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using Shift = SecurityApi.Dtos.Shift;
using Person = SecurityApi.Dtos.Person;
using Job = SecurityApi.Dtos.Job;
using State = SecurityApi.Dtos.State;
using Wage = SecurityApi.Dtos.Wage;
using Azure.Identity;
using System.Data;

namespace SecurityApi.Services
{
    public class ShiftService : IShiftService
    {
        private readonly OnlabContext _context;
        private const int PENDING_STATUS_ID = 1;
        public ShiftService(OnlabContext context)
        {
            _context = context;
        }
        public async Task<Shift> Create(CreateShift newShift)
        {
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var itsPerson = await _context.People.FirstOrDefaultAsync(p => p.Id == newShift.PersonId);

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

            if(itsWage == null || itsWage.Id == PENDING_STATUS_ID)
            {
                throw new Exception("Wage doesnt exist!");
            }

            var pendingState = await _context.States.FirstOrDefaultAsync(s => s.Id == 1);

            var shift = new Model.Shift()
            {
                StartTime = DateTime.Now,
                People = itsPerson,
                Job = itsJob,
                Wage = itsWage,
                Status = pendingState
            };

            _context.Shifts.Add(shift);

            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            return ToModel(shift);
        }

        public Task<Shift> Delete(int id)
        {
            throw new NotImplementedException();
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
