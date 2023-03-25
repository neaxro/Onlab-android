using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using Microsoft.Build.Framework;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Dashboard = SecurityApi.Dtos.Dashboard;

namespace SecurityApi.Controllers
{
    [Route("api/dashboard")]
    [ApiController]
    public class DashboardController : ControllerBase
    {
        private readonly IDashboardService _service;

        public DashboardController(IDashboardService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Dashboard>> GetAll()
        {
            var dboards = _service.ListAll();
            return Ok(dboards);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Dashboard>> GetById(int id)
        {
            var dboard = await _service.GetById(id);
            return dboard == null ? NotFound() : Ok(dboard);
        }

        [HttpGet("forperson/bycategoryid/{jobId}/{categoryId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<IEnumerable<Dashboard>>> GetForPersonByCategoryId(int jobId, int categoryId)
        {
            IEnumerable<Dashboard> dboards = _service.ListForPersonByCategoryID(jobId, categoryId);
            return Ok(dboards);
        }

        [HttpGet("forperson/bypersonid/{jobId}/{personId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<IEnumerable<Dashboard>>> GetForPersonByPersonId(int jobId, int personId)
        {
            var dboards = await _service.ListForPersonByPersonID(jobId, personId);
            return Ok(dboards);
        }

        [HttpGet("allforjob/{jobId}")]
        public ActionResult<IEnumerable<Dashboard>> GetAllForJob(int jobId)
        {
            var result = _service.ListAllInJob(jobId);
            return Ok(result);
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> InsertNewMessage([FromBody] CreateDashboard createDashboard)
        {
            try
            {
                var result = await _service.Insert(createDashboard);

                if(result == null)
                {
                    // This means that the server couldnt make a new message
                    return NotFound();
                }

                return CreatedAtAction(nameof(GetById), new { id = result.id }, result);
            }
            catch (ArgumentException ex)
            {
                ModelState.AddModelError(nameof(CreateDashboard.Title), ex.Message);
                return ValidationProblem(ModelState);
            }
            catch(Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPatch("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<Dashboard>> Update(int id, [FromBody] UpdateDashboard newContent)
        {
            try
            {
                var result = await _service.Update(id, newContent);
                return result == null ? NotFound() : Ok(result);
            } catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpDelete("{id}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Dashboard>> Remove(int id)
        {
            var result = await _service.Delete(id);

            return result == null ? NotFound() : NoContent();
        }
    }
}
