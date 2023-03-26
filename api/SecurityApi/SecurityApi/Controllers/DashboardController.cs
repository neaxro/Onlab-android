using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using Microsoft.Build.Framework;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos.DashboardDtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Dashboard = SecurityApi.Dtos.DashboardDtos.Dashboard;

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

        [HttpGet("{dashboardId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Dashboard>> GetById(int dashboardId)
        {
            try
            {
                var dboard = await _service.GetById(dashboardId);
                return Ok(dboard);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
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
            try
            {
                var dboards = await _service.ListForPersonByPersonID(jobId, personId);
                return Ok(dboards);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
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

                return CreatedAtAction(nameof(GetById), new { dashboardId = result.id }, result);
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

        [HttpPatch("{dashboardId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<Dashboard>> Update(int dashboardId, [FromBody] UpdateDashboard newContent)
        {
            try
            {
                var result = await _service.Update(dashboardId, newContent);
                return Ok(result);
            }
            catch (DataException de)
            {
                return NotFound(de.Message);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpDelete("{dashboardId}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Dashboard>> Remove(int dashboardId)
        {
            var result = await _service.Delete(dashboardId);

            return result == null ? NotFound() : NoContent();
        }
    }
}
