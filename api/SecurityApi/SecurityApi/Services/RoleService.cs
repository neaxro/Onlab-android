using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos.RoleDtos;
using Role = SecurityApi.Dtos.RoleDtos.Role;

namespace SecurityApi.Services
{
    public class RoleService : IRoleService
    {

        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;

        public RoleService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
        }

        public async Task<Role> Get(int roleId)
        {
            var role = await _context.Roles.FirstOrDefaultAsync(r => r.Id == roleId);
            if (role == null)
            {
                throw new Exception(String.Format("Role with ID({0}) does not exist!", roleId));
            }

            return _converter.ToModel(role);
        }

        public IEnumerable<Role> GetAll()
        {
            var roles = _context.Roles
                .Select(_converter.ToModel)
                .ToList();

            return roles;
        }
    }
}
