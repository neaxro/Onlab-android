using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json.Linq;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Person = SecurityApi.Dtos.Person;

namespace SecurityApi.Controllers
{
    [Route("api/people")]
    [ApiController]
    public class PeopleController : ControllerBase
    {
        private readonly IPeopleService _service;

        public PeopleController(IPeopleService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Person>> ListAll()
        {
            var people = _service.GetAll();
            return Ok(people);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Person>> Get(int id)
        {
            var talalat = await _service.FindById(id);
            if (talalat == null)
            {
                return NotFound();
            }
            else
            {
                return Ok(talalat);
            }
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<Person>> Insert([FromBody] CreatePerson uj)
        {
            try
            {
                var created = await _service.Insert(uj);
                return CreatedAtAction(nameof(Get), new { id = created.Id }, created);
            }
            catch (Exception ex)
            {
                //ModelState.AddModelError(nameof(CreatePerson.FullName), ex.Message);
                //return ValidationProblem(ModelState);

                return BadRequest(ex);
            }
        }

        [HttpPost("picture/{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [RequestSizeLimit(20000000)]  // ~20 MB
        public async Task<ActionResult> UploadProfilePicture(int id, [FromForm] IFormFile image)
        {
            var result = await _service.UploadImage(id, image);
            return result == null ? NotFound() : Ok();
        }

        [HttpPatch("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Person>> Update(int id, [FromBody] CreatePerson newData)
        {
            var person = await _service.Update(id, newData);

            return person == null ? NotFound() : Ok(person);
        }

        [HttpDelete("{id}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> Delete(int id)
        {
            try
            {
                var result = await _service.DeleteById(id);
                return result == null ? NotFound() : NoContent();
            }
            catch (Exception)
            {
                return BadRequest();
            }
        }        
    }
}
