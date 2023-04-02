using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Model;
using SecurityApi.Services;
using Status = SecurityApi.Dtos.StatusDtos.Status;

namespace SecurityApi.Controllers
{
    [Authorize]
    [Route("api/status")]
    [ApiController]
    public class StateController : ControllerBase
    {
        private readonly IStatusService _service;

        public StateController(IStatusService service)
        {
            _service = service;
        }

        [Authorize]
        [HttpGet]
        public ActionResult<IEnumerable<Status>> GetAll()
        {
            var states = _service.GetAll();
            return Ok(states);
        }

        [Authorize]
        [HttpGet("{statusId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Status>> Get(int statusId)
        {
            try
            {
                var result = await _service.Get(statusId);
                return Ok(result);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }
    }
}
