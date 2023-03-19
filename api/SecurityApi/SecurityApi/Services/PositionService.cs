using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
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

        public Task<Position> Cerate(int personId, CreatePosition newPosition)
        {
            throw new NotImplementedException();
        }

        public Task<Position> Delete(int positionId)
        {
            throw new NotImplementedException();
        }

        public async Task<Position> Get(int positionId)
        {
            var position = await _context.Positions.FirstOrDefaultAsync(p => p.Id == positionId);
            return position == null ? null : ToModel(position);
        }

        public IEnumerable<Position> GetAll()
        {
            var positions = _context.Positions.Select(ToModel).ToList();
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
