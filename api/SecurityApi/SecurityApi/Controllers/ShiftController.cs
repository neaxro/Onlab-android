using System;
using System.Collections.Generic;
using System.Composition;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos.ShiftDtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Shift = SecurityApi.Dtos.ShiftDtos.Shift;

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
            try
            {
                var shifts = _service.GetAllForJob(jobId);
                return Ok(shifts);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }
        
        [HttpGet("all/forperson/{personId}")]
        public ActionResult<IEnumerable<Shift>> GetAllForPerson(int personId)
        {
            try
            {
                var shifts = _service.GetAllForPerson(personId);
                return Ok(shifts);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpGet("all/{jobId}/{personId}")]
        public ActionResult<IEnumerable<Shift>> GetAllForPeronInJob(int jobId, int personId)
        {
            try
            {
                var shifts = _service.GetAllForPersonInJob(jobId, personId);
                return Ok(shifts);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpGet("pending/{jobId}")]
        public async Task<ActionResult<IEnumerable<Shift>>> GetAllPendingInJob(int jobId)
        {
            try
            {
                var pendingShifts = await _service.GetAllPendingInJob(jobId);
                return Ok(pendingShifts);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpGet("{shiftId}")]
        public async Task<ActionResult<Shift>> Get(int shiftId)
        {
            try
            {
                var result = await _service.Get(shiftId);
                return Ok(result);
            } catch(Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpGet("inprogress/{jobId}")]
        public ActionResult<IEnumerable<Shift>> GetAllInProgressShifts(int jobId)
        {
            var result = _service.GetAllInProgress(jobId);
            return Ok(result);
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<Shift>> CreateShift([FromBody] CreateShift shift)
        {
            try
            {
                var created = await _service.Create(shift);
                return CreatedAtAction(nameof(Get), new { shiftId = created.Id }, created);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPatch("finish/{shiftId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Shift>> FinishShift(int shiftId)
        {
            try
            {
                var result = await _service.Finish(shiftId);
                return Ok(result);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpPatch("{shiftId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult> Update(int shiftId, [FromBody] UpdateShift updateShift)
        {
            try
            {
                var res = await _service.Update(shiftId, updateShift);
                return Ok(res);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPatch("accept/{shiftId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult> AcceptShift(int shiftId)
        {
            try
            {
                var res = await _service.AcceptShift(shiftId);
                return Ok();
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpPatch("deny/{shiftId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult> DenyShift(int shiftId)
        {
            try
            {
                var res = await _service.DenyShift(shiftId);
                return Ok();
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpDelete("{shiftId}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Shift>> Delete(int shiftId)
        {
            try
            {
                var result = await _service.Delete(shiftId);
                return Ok(result);
            } catch(Exception ex)
            {
                return NotFound(ex.Message);
            }
        }
    }
}
