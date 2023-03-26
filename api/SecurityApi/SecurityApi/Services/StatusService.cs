using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos.StatusDtos;
using Status = SecurityApi.Dtos.StatusDtos.Status;

namespace SecurityApi.Services
{
    public class StatusService : IStatusService
    {
        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;
        public StatusService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
        }

        public async Task<Status> Get(int statusId)
        {
            var status = await _context.States.FirstOrDefaultAsync(s => s.Id == statusId);
            if (status == null)
            {
                throw new Exception(String.Format("Status with ID({0}) does not exist!", statusId));
            }

            return _converter.ToModel(status);
        }

        public IEnumerable<Status> GetAll()
        {
            var states = _context.States
                .Select(_converter.ToModel)
                .ToList();

            return states;
        }
    }
}
