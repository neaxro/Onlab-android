using SecurityApi.Dtos.PositionDtos;
using SecurityApi.Model;
using Position = SecurityApi.Dtos.PositionDtos.Position;

namespace SecurityApi.Services
{
    public interface IPositionService
    {
        Task<Position> Get(int positionId);
        IEnumerable<Position> GetAll();
        IEnumerable<Position> GetAllForPerson(int jobId, int personId);
        IEnumerable<Position> GetAllLatestForAll(int jobId);
        Task<Position> Create(int jobId, int personId, CreatePosition newPosition);
        Task<Position> Update(int positionId, CreatePosition newPosition);
        Task<Position> Delete(int positionId);
    }
}
