using SecurityApi.Dtos.StatusDtos;
using Status = SecurityApi.Dtos.StatusDtos.Status;

namespace SecurityApi.Services
{
    public interface IStatusService
    {
        IEnumerable<Status> GetAll();
        Task<Status> Get(int statusId);
    }
}
