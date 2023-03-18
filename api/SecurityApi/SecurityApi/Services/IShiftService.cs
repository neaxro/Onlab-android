using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public interface IShiftService
    {
        IEnumerable<Shift> GetAll();
        Task<Shift> Get(int id);
        IEnumerable<Shift> GetAllForJob(int jobId);
        IEnumerable<Shift> GetAllForPerson(int personId);
        IEnumerable<Shift> GetAllForPersonInJob(int personId, int jobId);
        Task<IEnumerable<Shift>> GetAllPendingInJob(int jobId);
        Task<Shift> Create(int id, CreateShift newShift);
        Task<Shift> Finish(int personId);
        Task<Shift> Delete(int id);
        Task<Shift> Update(int id, UpdateShift newShift);
        Task<Shift> AcceptShift(int id);
        Task<Shift> DenyShift(int id);
    }
}
