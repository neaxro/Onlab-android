﻿using SecurityApi.Dtos.RoleDtos;
using Role = SecurityApi.Dtos.RoleDtos.Role;

namespace SecurityApi.Services
{
    public interface IRoleService
    {
        IEnumerable<Role> GetAll();
        IEnumerable<Role> GetAllChoosable();

        Task<Role> Get(int roleId);
    }
}
