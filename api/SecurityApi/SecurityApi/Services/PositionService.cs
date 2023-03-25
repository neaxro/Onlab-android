using Microsoft.CodeAnalysis.CSharp.Formatting;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using System.Data;
using System.Runtime.CompilerServices;
using Position = SecurityApi.Dtos.PositionDtos.Position;
using Person = SecurityApi.Dtos.PersonDtos.Person;
using Job = SecurityApi.Dtos.JobDtos.Job;
using SecurityApi.Dtos.PositionDtos;

namespace SecurityApi.Services
{
    public class PositionService : IPositionService
    {
        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;
        public PositionService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
        }

        public async Task<Position> Create(int jobId, int personId, CreatePosition newPosition)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);
            if (person == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Person with ID({0}) not found!", personId));
            }

            var job = await _context.Jobs
                .Include(j => j.People)
                .FirstOrDefaultAsync(j => j.Id == jobId);
            if(job == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Job with ID({0}) not found!", jobId));
            }

            var personInJob = await _context.PeopleJobs.FirstOrDefaultAsync(pj => pj.JobId == jobId && pj.PeopleId == personId);
            if(personInJob == null)
            {
                await tran.RollbackAsync();
                throw new Exception("People does not exist in Job!");
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

            return _converter.ToModel(position);
        }

        public async Task<Position> Delete(int positionId)
        {
            var position = await _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .FirstOrDefaultAsync(p => p.Id == positionId);
            
            if(position == null)
            {
                throw new Exception(String.Format("Position with ID({0}) does not exist!", positionId));
            }
            
            _context.Positions.Remove(position);
            await _context.SaveChangesAsync();
            
            return _converter.ToModel(position);
        }

        public async Task<Position> Get(int positionId)
        {
            var position = await _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .FirstOrDefaultAsync(p => p.Id == positionId);

            if (position == null)
            {
                throw new Exception(String.Format("Position with ID({0}) does not exist!", positionId));
            }

            return _converter.ToModel(position);
        }

        public IEnumerable<Position> GetAll()
        {
            var positions = _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .Select(_converter.ToModel)
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
                .Select(_converter.ToModel)
                .ToList();

            return positions;
        }

        public IEnumerable<Position> GetAllLatestForAll(int jobId)
        {
            var grouppedPositions = _context.Positions
                .Include(p => p.People)
                .Where(p => p.JobId == jobId)
                .GroupBy(pos => pos.People.Id)
                .Select(g => new
                {
                    Id = g.Key,
                    Time = g.Max(row => row.Time),
                })
                .ToList();

            var pos = _context.Positions
                .Include(p => p.People)
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .ToList();

            var joined = from positions in pos
                         join groupped in grouppedPositions on new
                         {
                             PeopleID = (int)positions.PeopleId,
                             Time = positions.Time
                         } equals new
                         {
                             PeopleID =  groupped.Id,
                             Time = groupped.Time
                         }
                         select new
                         {
                             Id = positions.Id,
                             Time = positions.Time,
                             Longitude = positions.Longitude,
                             Latitude = positions.Latitude,
                             Person = positions.People,
                             Job = positions.Job
                         };

            List<Position> positionsForReturn = new List<Position>();

            foreach(var entity in joined)
            {
                var person = new Person(
                    entity.Person.Id,
                    entity.Person.Name,
                    entity.Person.Username,
                    entity.Person.Nickname,
                    entity.Person.Email,
                    entity.Person.ProfilePicture);

                var owner = new Person(
                    entity.Job.People.Id,
                    entity.Job.People.Name,
                    entity.Job.People.Username,
                    entity.Job.People.Nickname,
                    entity.Job.People.Email,
                    entity.Job.People.ProfilePicture);


                var job = new Job(entity.Job.Id, entity.Job.Title, entity.Job.Pin, entity.Job.Description, owner);

                positionsForReturn.Add(new Position(
                    entity.Id,
                    entity.Time,
                    entity.Longitude,
                    entity.Latitude,
                    person,
                    job));
            }

            return positionsForReturn;
        }

        public async Task<Position> Update(int positionId, CreatePosition newPosition)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var position = await _context.Positions
                .Include(p => p.Job)
                    .ThenInclude(j => j.People)
                .Include(p => p.People)
                .FirstOrDefaultAsync(p => p.Id == positionId);

            if(position == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Position with ID({0}) does not exist!", positionId));
            }

            position.Longitude = newPosition.Longitude;
            position.Latitude = newPosition.Latitude;

            await _context.SaveChangesAsync();
            await tran.CommitAsync();

            return _converter.ToModel(position);
        }
    }
}
