using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.ShiftDtos;

namespace SecurityApi.Services
{
    public interface IShiftService
    {
        IEnumerable<Shift> GetAll();
        Task<Shift> Get(int shiftId);
        IEnumerable<Shift> GetAllForJob(int jobId);
        IEnumerable<Shift> GetAllForPerson(int personId);
        IEnumerable<Shift> GetAllShiftsForPersonInJob(int jobId, int personId);
        IEnumerable<Shift> GetAcceptedShiftsForPersonInJob(int jobId, int personId);
        IEnumerable<Shift> GetDeniedShiftsForPersonInJob(int jobId, int personId);
        IEnumerable<Shift> GetAllInProgress(int jobId);
        Task<Shift> GetInProgressForPersonInJob(int jobId, int personId);
        Task<IEnumerable<Shift>> GetAllPendingInJob(int jobId);
        Task<Shift> Create(CreateShift newShift);
        Task<Shift> Finish(int shiftId);
        Task<Shift> Delete(int shiftId);
        Task<Shift> Update(int shiftId, UpdateShift newShift);
        Task<Shift> AcceptShift(int shiftId);
        Task<Shift> DenyShift(int shiftId);
        Task WageChangedUpdate(int wageId);
        JobStatistic GetJobStatistic(int jobId);
    }
}
