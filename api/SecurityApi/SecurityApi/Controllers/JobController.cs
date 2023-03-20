using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Job = SecurityApi.Dtos.Job;

namespace SecurityApi.Controllers
{
    [Route("api/job")]
    [ApiController]
    public class JobController : ControllerBase
    {
        private readonly IJobService _service;

        public JobController(IJobService service)
        {
            _service = service;
        }

        [HttpGet("all")]
        public ActionResult<IEnumerable<Job>> GetAllJobs()
        {
            var jobs = _service.GetAll();
            return Ok(jobs);
        }

        [HttpGet("{jobId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Job>> Get(int jobId)
        {
            var result = await _service.Get(jobId);
            return result == null ? NotFound() : Ok(result);
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Job>> Create([FromBody] CreateJob job)
        {
            try
            {
                var created = await _service.Create(job);
                return CreatedAtAction(nameof(Get), new { jobId = created.Id }, created);
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
    }
}
