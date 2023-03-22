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
using SecurityApi.Dtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Wage = SecurityApi.Dtos.Wage;

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

        [HttpGet]
        public ActionResult<IEnumerable<Wage>> GetWages()
        {
            var wages = _service.GetWages();
            return Ok(wages);
        }

        [HttpGet("all")]
        public ActionResult<IEnumerable<Wage>> GetAll()
        {
            var wages = _service.GetAll();
            return Ok(wages);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Wage>> GetWageById(int id)
        {
            var result = await _service.GetById(id);
            return result == null ? NotFound() : Ok(result);
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
            } catch (DataException de)
            {
                return NotFound(de.Message);
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
            catch (DataException de)
            {
                return NotFound(de.Message);
            }
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public async Task<ActionResult<Wage>> Insert([FromBody] CreateWage newWage)
        {
            try
            {
                var created = await _service.Create(newWage);

                if (created == null)
                {
                    // This means that the server couldnt make a new message
                    return NotFound();
                }

                return CreatedAtAction(nameof(GetWageById), new { id = created.Id }, created);
            }
            catch(ArgumentOutOfRangeException ec)
            {
                return BadRequest(ec.Message);
            }
            catch (DataException de)
            {
                return BadRequest(de.Message);
            }
            catch (ArgumentException ex)
            {
                ModelState.AddModelError(nameof(CreateWage.Name), ex.Message);
                return ValidationProblem(ModelState);
            }
            catch(Exception e)
            {
                return BadRequest($"Could not create {e.Message}");
            }
        }

        [HttpPatch("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> Update(int id, [FromBody] CreateWage newContent)
        {
            try
            {
                var result = await _service.Update(id, newContent);
                return result == null ? NotFound() : Ok();
            }
            catch(DataException de)
            {
                return BadRequest(de.Message);
            }
            catch (ArgumentOutOfRangeException ec)
            {
                return BadRequest(ec.Message);
            }

        }

        [HttpDelete("{id}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Wage>> Delete(int id)
        {
            var result = await _service.Delete(id);
            return result == null ? NotFound() : NoContent();
        }
    }
}
