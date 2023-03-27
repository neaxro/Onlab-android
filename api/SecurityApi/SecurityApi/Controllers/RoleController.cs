using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using NuGet.Protocol;
using SecurityApi.Context;
using SecurityApi.Model;
using SecurityApi.Services;
using Role = SecurityApi.Dtos.RoleDtos.Role;

namespace SecurityApi.Controllers
{
    [Authorize]
    [Route("api/role")]
    [ApiController]
    public class RoleController : ControllerBase
    {
        private readonly IRoleService _service;
        public RoleController(IRoleService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Role>> GetAll()
        {
            var roles = _service.GetAll();

            return Ok(roles);
        }

        [HttpGet("{roleId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Role>> Get(int roleId)
        {
            try
            {
                var result = await _service.Get(roleId);
                return Ok(result);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }
    }
}
