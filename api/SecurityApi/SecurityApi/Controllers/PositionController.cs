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
using Position = SecurityApi.Dtos.Position;

namespace SecurityApi.Controllers
{
    [Route("api/position")]
    [ApiController]
    public class PositionController : ControllerBase
    {
        private readonly IPositionService _service;

        public PositionController(IPositionService service)
        {
            _service = service;
        }

        [HttpGet("all")]
        public ActionResult<IEnumerable<Position>> GetAll()
        {
            var positions = _service.GetAll();
            return Ok(positions);
        }

        [HttpGet("{positionId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<IEnumerable<Position>>> GetById(int positionId)
        {
            var position = await _service.Get(positionId);
            return position == null ? NotFound() : Ok(position);
        }

        [HttpGet("forperson/{jobId}/{personId}")]
        public ActionResult<IEnumerable<Position>> GetAllForPerson(int jobId, int personId)
        {
            var positions = _service.GetAllForPerson(jobId, personId);
            return Ok(positions);
        }

        [HttpGet("latest/{jobId}")]
        public ActionResult<IEnumerable<Position>> GetLatestPositionsForJob(int jobId)
        {
            var result = _service.GetAllLatestForAll(jobId);
            return Ok(result);
        }

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
            catch (ArgumentException ae)
            {
                return NotFound(ae.Message);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPatch("{positionId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Position>> Update(int positionId, [FromBody] CreatePosition position)
        {
            var positon = await _service.Update(positionId, position);
            return position == null ? NotFound() : Ok(positon);
        }

        [HttpDelete("{id}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Position>> Delete(int id)
        {
            var result = await _service.Delete(id);
            return result == null ? NotFound() : Ok(result);
        }
    }
}
