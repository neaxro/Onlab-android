using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public interface IShiftService
    {
        IEnumerable<Shift> GetAll();
        IEnumerable<Shift> Get(int id);
        IEnumerable<Shift> GetAllForJob(int jobId);
        IEnumerable<Shift> GetAllForPerson(int personId);
        IEnumerable<Shift> GetAllForPersonInJob(int personId, int jobId);
        Task<IEnumerable<Shift>> GetAllPendingInJob(int jobId);
        Task<Shift> Create(CreateShift newShift);
        Task<Shift> Delete(int id);
        Task<Shift> Update(UpdateShift newShift);
    }
}
