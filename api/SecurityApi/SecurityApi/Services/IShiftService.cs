using SecurityApi.Dtos.ShiftDtos;

namespace SecurityApi.Services
{
    public interface IShiftService
    {
        IEnumerable<Shift> GetAll();
        Task<Shift> Get(int shiftId);
        IEnumerable<Shift> GetAllForJob(int jobId);
        IEnumerable<Shift> GetAllForPerson(int personId);
        IEnumerable<Shift> GetAllForPersonInJob(int jobId, int personId);
        IEnumerable<Shift> GetAllInProgress(int jobId);
        Task<IEnumerable<Shift>> GetAllPendingInJob(int jobId);
        Task<Shift> Create(CreateShift newShift);
        Task<Shift> Finish(int shiftId);
        Task<Shift> Delete(int shiftId);
        Task<Shift> Update(int shiftId, UpdateShift newShift);
        Task<Shift> AcceptShift(int shiftId);
        Task<Shift> DenyShift(int shiftId);
        Task WageChangedUpdate(int wageId);
    }
}
