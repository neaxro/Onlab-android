using Microsoft.CodeAnalysis.CSharp.Formatting;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using System.Data;
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

        public async Task<Position> Create(int personId, CreatePosition newPosition)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);
            if (person == null)
            {
                throw new ArgumentException(String.Format("Person with ID({0}) not found!", personId));
            }

            var position = new Model.Position()
            {
                Time = DateTime.Now,
                Longitude = newPosition.Longitude,
                Latitude = newPosition.Latitude,
                People = person
            };

            await _context.Positions.AddAsync(position);
            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            return ToModel(position);
        }

        public async Task<Position> Delete(int positionId)
        {
            var position = await _context.Positions
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
                .Include(p => p.People)
                .FirstOrDefaultAsync(p => p.Id == positionId);
            return position == null ? null : ToModel(position);
        }

        public IEnumerable<Position> GetAll()
        {
            var positions = _context.Positions
                .Include(p => p.People)
                .Select(ToModel)
                .ToList();
            return positions;
        }

        public Task<IEnumerable<Position>> GetAllForPerson(int personId)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Position>> GetAllLatestForAll()
        {
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

            return new Position(position.Id, (DateTime)position.Time, (float)position.Longitude, (float)position.Latitude, p);
        }
    }
}
