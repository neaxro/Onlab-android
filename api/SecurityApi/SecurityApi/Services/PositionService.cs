using SecurityApi.Context;
using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public class PositionService : IPositionService
    {
        private readonly OnlabContext _context;
        public PositionService(OnlabContext context)
        {
            _context = context;
        }

        public Task<Model.Position> Cerate(int personId, CreatePosition newPosition)
        {
            throw new NotImplementedException();
        }

        public Task<Model.Position> Delete(int positionId)
        {
            throw new NotImplementedException();
        }

        public Task<Model.Position> Get(int positionId)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Model.Position>> GetAll()
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Model.Position>> GetAllForPerson(int personId)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Model.Position>> GetAllLatestForAll()
        {
            throw new NotImplementedException();
        }

        public Task<Model.Position> Update(int positionId, CreatePosition newPosition)
        {
            throw new NotImplementedException();
        }
    }
}
