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
using SecurityApi.Enums;
using SecurityApi.Model;
using SecurityApi.Services;
using Job = SecurityApi.Dtos.Job;
using Person = SecurityApi.Dtos.Person;

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

        [HttpGet("allonjob/{jobId}")]
        public ActionResult<IEnumerable<Person>> GetAllOnJob(int jobId)
        {
            var result = _service.AllPersonInJob(jobId);
            return Ok(result);
        }

        [HttpGet("{jobId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Job>> Get(int jobId)
        {
            var result = await _service.Get(jobId);
            return result == null ? NotFound() : Ok(result);
        }

        [HttpGet("availablefor/{personId}")]
        public ActionResult<IEnumerable<Job>> GetAllAvailableForPerson(int personId)
        {
            var result = _service.GetAllAwailableForPerson(personId);
            return Ok(result);
        }

        [HttpGet("connection/{connectionId}")]
        public async Task<ActionResult<PersonJob>> GetConnection(int connectionId)
        {
            var result = await _service.GetConnection(connectionId);
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

        [HttpPost("connect/{pin}/{personId}")]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Job>> ConncetPersonToJob(string pin, int personId)
        {
            try
            {
                var created = await _service.ConnectToJob(pin.Trim(), personId, DatabaseConstants.ROLE_USER_ID);
                return CreatedAtAction(nameof(GetConnection), new { connectionId = created.Id }, created);
            }
            catch (DataException de)
            {
                return BadRequest(de.Message);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPatch("changerole/{jobId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> ChangeRole(int jobId, ChangeRole change)
        {
            try
            {
                var result = await _service.ChangePersonRole(jobId, change);
                return Ok();
            } catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPatch("changewage/{jobId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> ChangeWage(int jobId, ChangeWage change)
        {
            try
            {
                var result = await _service.ChangePersonWage(jobId, change);
                return Ok();
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
