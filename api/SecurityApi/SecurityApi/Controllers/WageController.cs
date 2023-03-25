using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.TagHelpers;
using Microsoft.Build.Framework;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos.WageDtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Wage = SecurityApi.Dtos.WageDtos.Wage;

namespace SecurityApi.Controllers
{
    [Route("api/wage")]
    [ApiController]
    public class WageController : ControllerBase
    {
        private readonly IWageService _service;

        public WageController(IWageService service)
        {
            _service = service;
        }

        [HttpGet("all")]
        public ActionResult<IEnumerable<Wage>> GetAll()
        {
            var wages = _service.GetAll();
            return Ok(wages);
        }

        [HttpGet("{wageId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Wage>> GetWageById(int wageId)
        {
            try
            {
                var result = await _service.GetById(wageId);
                return Ok(result);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpGet("forjob/{jobId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<IEnumerable<Wage>>> GetWagesForJob(int jobId)
        {
            try
            {
                var result = await _service.GetWagesInJob(jobId);
                return Ok(result);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpGet("categories/{jobId}")]
        public async Task<ActionResult<IEnumerable<MessageCategory>>> GetMessageCategories(int jobId)
        {
            try
            {
                var result = await _service.GetMessageCategories(jobId);
                return Ok(result);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public async Task<ActionResult<Wage>> Insert([FromBody] CreateWage newWage)
        {
            try
            {
                var created = await _service.Create(newWage);
                return CreatedAtAction(nameof(GetWageById), new { wageId = created.Id }, created);
            }
            catch(Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [HttpPatch("{wageId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> Update(int wageId, [FromBody] UpdateWage newContent)
        {
            try
            {
                var result = await _service.Update(wageId, newContent);
                return Ok(result);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }

        }

        [HttpDelete("{wageId}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Wage>> Delete(int wageId)
        {
            try
            {
                var result = await _service.Delete(wageId);
                return NoContent();
            } catch(Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }
    }
}
