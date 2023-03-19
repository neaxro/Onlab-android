using SecurityApi.Dtos;
using SecurityApi.Model;
using Position = SecurityApi.Model.Position;

namespace SecurityApi.Services
{
    public interface IPositionService
    {
        Task<Position> Get(int positionId);
        Task<IEnumerable<Position>> GetAll();
        Task<IEnumerable<Position>> GetAllForPerson(int personId);
        Task<IEnumerable<Position>> GetAllLatestForAll();
        Task<Position> Cerate(int personId, CreatePosition newPosition);
        Task<Position> Update(int positionId, CreatePosition newPosition);
        Task<Position> Delete(int positionId);
    }
}
