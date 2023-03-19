using Microsoft.CodeAnalysis.CSharp.Formatting;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using System.Data;
using System.Runtime.CompilerServices;
using Position = SecurityApi.Dtos.Position;

namespace SecurityApi.Services
{
    public class PositionService : IPositionService
    {
        private readonly OnlabContext _context;
        public PositionService(OnlabContext context)
        {
            _context = context;
        }

        public async Task<Position> Create(int jobId, int personId, CreatePosition newPosition)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);
            if (person == null)
            {
                throw new ArgumentException(String.Format("Person with ID({0}) not found!", personId));
            }

            var job = await _context.Jobs
                .Include(j => j.People)
                .FirstOrDefaultAsync(j => j.Id == jobId);
            if(job == null)
            {
                throw new ArgumentException(String.Format("Job with ID({0}) not found!", jobId));
            }

            var position = new Model.Position()
            {
                Time = DateTime.Now,
                Longitude = newPosition.Longitude,
                Latitude = newPosition.Latitude,
                People = person,
                Job = job
            };

            await _context.Positions.AddAsync(position);
            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            return ToModel(position);
        }

        public async Task<Position> Delete(int positionId)
        {
            var position = await _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .FirstOrDefaultAsync(p => p.Id == positionId);
            
            if(position != null)
            {
                _context.Positions.Remove(position);
                await _context.SaveChangesAsync();
            }
            
            return position == null ? null : ToModel(position);
        }

        public async Task<Position> Get(int positionId)
        {
            var position = await _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .FirstOrDefaultAsync(p => p.Id == positionId);
            return position == null ? null : ToModel(position);
        }

        public IEnumerable<Position> GetAll()
        {
            var positions = _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .Select(ToModel)
                .ToList();
            return positions;
        }

        public IEnumerable<Position> GetAllForPerson(int jobId, int personId)
        {
            var positions = _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .Where(p => p.PeopleId == personId && p.JobId == jobId)
                .Select(ToModel)
                .ToList();

            return positions;
        }

        public Task<IEnumerable<Position>> GetAllLatestForAll(int jobId)
        {
            var latestPositions = _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .Where(p => p.JobId == jobId)
                .GroupBy(pos => pos.Time)
                .OrderByDescending(group => group.Max(g => g.Time))
                .ToList();

            throw new NotImplementedException();
        }

        public Task<Position> Update(int positionId, CreatePosition newPosition)
        {
            throw new NotImplementedException();
        }

        private Position ToModel(Model.Position position)
        {
            var p = new Person(
                position.People.Id,
                position.People.Name,
                position.People.Username,
                position.People.Nickname,
                position.People.Email,
                null
                //position.People.ProfilePicture
                );

            var owner = new Person(
                position.Job.People.Id,
                position.Job.People.Name,
                position.Job.People.Username,
                position.Job.People.Nickname,
                position.Job.People.Email,
                null
                //position.Job.People.ProfilePicture
                );

            var j = new Job(
                position.Job.Id,
                position.Job.Title,
                position.Job.Description,
                owner
                );

            return new Position(position.Id, (DateTime)position.Time, (float)position.Longitude, (float)position.Latitude, p, j);
        }
    }
}
