using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos.PositionDtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Position = SecurityApi.Dtos.PositionDtos.Position;

namespace SecurityApi.Controllers
{
    [Authorize]
    [Route("api/position")]
    [ApiController]
    public class PositionController : ControllerBase
    {
        private readonly IPositionService _service;

        public PositionController(IPositionService service)
        {
            _service = service;
        }

        [Authorize(Roles = "Admin,Owner")]
        [HttpGet("all")]
        public ActionResult<IEnumerable<Position>> GetAll()
        {
            var positions = _service.GetAll();
            return Ok(positions);
        }

        [Authorize(Roles = "Admin,Owner")]
        [HttpGet("{positionId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<IEnumerable<Position>>> GetById(int positionId)
        {
            try
            {
                var position = await _service.Get(positionId);
                return Ok(position);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize(Roles = "Admin,Owner")]
        [HttpGet("forperson/{jobId}/{personId}")]
        public ActionResult<IEnumerable<Position>> GetAllForPerson(int jobId, int personId)
        {
            var positions = _service.GetAllForPerson(jobId, personId);
            return Ok(positions);
        }

        [Authorize(Roles = "Admin,Owner")]
        [HttpGet("latest/{jobId}")]
        public ActionResult<IEnumerable<Position>> GetLatestPositionsForJob(int jobId)
        {
            var result = _service.GetAllLatestForAll(jobId);
            return Ok(result);
        }

        [Authorize]
        [HttpPost("{jobId}/{personId}")]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Position>> Create(int jobId, int personId, [FromBody] CreatePosition newPosition)
        {
            try
            {
                var created = await _service.Create(jobId, personId, newPosition);
                return CreatedAtAction(nameof(GetById), new { positionId = created.Id }, created);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [Authorize(Roles = "Admin,Owner")]
        [HttpPatch("{positionId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Position>> Update(int positionId, [FromBody] CreatePosition position)
        {
            try
            {
                var positon = await _service.Update(positionId, position);
                return Ok(positon);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize(Roles = "Admin,Owner")]
        [HttpDelete("{positionId}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Position>> Delete(int positionId)
        {
            try
            {
                var result = await _service.Delete(positionId);
                return Ok(result);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }
    }
}
