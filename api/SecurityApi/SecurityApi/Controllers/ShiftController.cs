using System;
using System.Collections.Generic;
using System.Composition;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
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
    [Authorize]
    [Route("api/shift")]
    [ApiController]
    public class ShiftController : ControllerBase
    {
        private readonly IShiftService _service;

        public ShiftController(IShiftService service)
        {
            _service = service;
        }

        // DEBUG ONLY
        [AllowAnonymous]
        [HttpGet("all")]
        public ActionResult<IEnumerable<Shift>> GetAll()
        {
            var shifts = _service.GetAll();
            return Ok(shifts);
        }

        [Authorize(Roles = "Admin,Owner")]
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

        [Authorize(Roles = "Admin,Owner")]
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

        [Authorize]
        [HttpGet("all/{jobId}/{personId}")]
        public ActionResult<IEnumerable<Shift>> GetAllShiftsForPersonInJob(int jobId, int personId)
        {
            try
            {
                var shifts = _service.GetAllShiftsForPersonInJob(jobId, personId);
                return Ok(shifts);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize]
        [HttpGet("accepted/{jobId}/{personId}")]
        public ActionResult<IEnumerable<Shift>> GetAcceptedShiftsForPersonInJob(int jobId, int personId)
        {
            try
            {
                var shifts = _service.GetAcceptedShiftsForPersonInJob(jobId, personId);
                return Ok(shifts);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize]
        [HttpGet("denied/{jobId}/{personId}")]
        public ActionResult<IEnumerable<Shift>> GetDeniedShiftsForPersonInJob(int jobId, int personId)
        {
            try
            {
                var shifts = _service.GetDeniedShiftsForPersonInJob(jobId, personId);
                return Ok(shifts);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize(Roles = "Admin,Owner")]
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

        [Authorize]
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

        [Authorize(Roles = "Admin,Owner")]
        [HttpGet("inprogress/{jobId}")]
        public ActionResult<IEnumerable<Shift>> GetAllInProgressShifts(int jobId)
        {
            var result = _service.GetAllInProgress(jobId);
            return Ok(result);
        }

        [Authorize(Roles = "Admin,Owner")]
        [HttpGet("inprogress/{jobId}/forperson/{personId}")]
        public async Task<ActionResult<Shift>> GetInProgressForPersonInJob(int jobId, int personId)
        {
            try
            {
                var result = await _service.GetInProgressForPersonInJob(jobId, personId);
                return Ok(result);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize]
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

        [Authorize]
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

        [Authorize(Roles = "Admin,Owner")]
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

        [Authorize(Roles = "Admin,Owner")]
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

        [Authorize(Roles = "Admin,Owner")]
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

        [Authorize(Roles = "Admin,Owner")]
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
