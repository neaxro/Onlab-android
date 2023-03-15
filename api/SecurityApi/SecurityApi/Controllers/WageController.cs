using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
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

        [HttpGet("all")]
        public ActionResult<IEnumerable<Wage>> GetAll()
        {
            var wages = _service.GetAll();
            return Ok(wages);
        }
        [HttpGet]
        public ActionResult<IEnumerable<Wage>> GetWages()
        {
            var wages = _service.GetWages();
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
            catch (ArgumentException ex)
            {
                ModelState.AddModelError(nameof(CreateWage.Name), ex.Message);
                return ValidationProblem(ModelState);
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
