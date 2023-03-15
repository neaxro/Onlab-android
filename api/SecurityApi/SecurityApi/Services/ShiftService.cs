using SecurityApi.Context;
using SecurityApi.Dtos;

namespace SecurityApi.Services
{
    public class ShiftService : IShiftService
    {
        private readonly OnlabContext _context;
        public ShiftService(OnlabContext context)
        {
            _context = context;
        }
        public Task<Shift> Create(CreateShift newShift)
        {
            throw new NotImplementedException();
        }

        public Task<Shift> Delete(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Shift> Get(int id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Shift> GetAll()
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Shift> GetAllForJob(int jobId)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Shift> GetAllForPerson(int personId)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Shift> GetAllForPersonInJob(int personId, int jobId)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Shift> GetAllPendingInJob(int jobId)
        {
            throw new NotImplementedException();
        }

        public Task<Shift> Update(UpdateShift newShift)
        {
            throw new NotImplementedException();
        }
    }
}
