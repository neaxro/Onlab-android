using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Shift = SecurityApi.Dtos.Shift;

namespace SecurityApi.Controllers
{
    [Route("api/shift")]
    [ApiController]
    public class ShiftController : ControllerBase
    {
        private readonly IShiftService _service;

        public ShiftController(IShiftService service)
        {
            _service = service;
        }

        [HttpGet("all")]
        public ActionResult<IEnumerable<Shift>> GetAll()
        {
            var shifts = _service.GetAll();
            return Ok(shifts);
        }

        [HttpGet("all/forjob/{jobId}")]
        public ActionResult<IEnumerable<Shift>> GetAllForJob(int jobId)
        {
            var shifts = _service.GetAllForJob(jobId);
            return shifts == null ? NotFound() : Ok(shifts);
        }
        
        [HttpGet("all/forperson/{personId}")]
        public ActionResult<IEnumerable<Shift>> GetAllForPerson(int personId)
        {
            var shifts = _service.GetAllForPerson(personId);
            return shifts == null ? NotFound() : Ok(shifts);
        }

        [HttpGet("all/{personId}/{jobId}")]
        public ActionResult<IEnumerable<Shift>> GetAllFroPeronInJob(int personId, int jobId)
        {
            var shifts = _service.GetAllForPersonInJob(personId, jobId);
            return shifts == null ? NotFound() : Ok(shifts);
        }

        [HttpGet("pending/{jobId}")]
        public async Task<ActionResult<IEnumerable<Shift>>> GetAllPendingInJob(int jobId)
        {
            var pendingShifts = await _service.GetAllPendingInJob(jobId);
            return pendingShifts == null ? NotFound() : Ok(pendingShifts);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<Shift>> Get(int id)
        {
            var result = await _service.Get(id);
            return result == null ? NotFound() : Ok(result);
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<Shift>> CreateShift([FromBody] CreateShift shift)
        {
            try
            {
                var created = await _service.Create(shift);
                return CreatedAtAction(nameof(Get), new { id = created.Id }, created);
            }
            catch (Exception ex)
            {
                return BadRequest();
            }
        }

        [HttpPatch("{personId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Shift>> EndShift(int personId)
        {
            var result = await _service.Finish(personId);
            return result == null ? NotFound() : Ok(result);
        }
    }
}
