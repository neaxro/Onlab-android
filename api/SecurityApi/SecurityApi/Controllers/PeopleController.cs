using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Newtonsoft.Json.Linq;
using SecurityApi.Context;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Person = SecurityApi.Dtos.PersonDtos.Person;

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

        // DEBUG ONLY
        [AllowAnonymous]
        [HttpGet("test")]
        public ActionResult<String> Test()
        {
            return Ok("Succesfull test :)");
        }

        // DEBUG ONLY
        [AllowAnonymous]
        [HttpGet]
        public ActionResult<IEnumerable<Person>> ListAll()
        {
            var people = _service.GetAll();
            return Ok(people);
        }

        [Authorize]
        [HttpGet("{personId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Person>> Get(int personId)
        {
            try
            {
                var talalat = await _service.Get(personId);
                return Ok(talalat);
            } catch(Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [AllowAnonymous]
        [HttpPost("register")]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult<Person>> Register([FromBody] CreatePerson newPerson)
        {
            try
            {
                var created = await _service.Register(newPerson);
                return CreatedAtAction(nameof(Get), new { personId = created.Id }, created);
            }
            catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [AllowAnonymous]
        [HttpPost("login")]
        public async Task<ActionResult<LoginResponse>> Login([FromBody] LoginPerson loginData)
        {
            try
            {
                var result = await _service.Login(loginData);
                return Ok(result);
            } catch (Exception ex)
            {
                return BadRequest(ex.Message);
            }
        }

        [Authorize]
        [HttpPost("picture/{personId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [RequestSizeLimit(20000000)]  // ~20 MB
        public async Task<ActionResult> UploadProfilePicture(int personId, [FromForm] IFormFile image)
        {
            try{
                var result = await _service.UploadImage(personId, image);
                return Ok();
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize]
        [HttpPatch("{personId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Person>> Update(int personId, [FromBody] CreatePerson newData)
        {
            try
            {
                var person = await _service.Update(personId, newData);
                return Ok(person);
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize]
        [HttpDelete("{personId}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> Delete(int personId)
        {
            try
            {
                var result = await _service.Delete(personId);
                return NoContent();
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize]
        [HttpDelete("profilepicture/{personId}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        [ProducesResponseType(StatusCodes.Status400BadRequest)]
        public async Task<ActionResult> DeleteProfilePicture(int personId)
        {
            try
            {
                var result = await _service.RemoveImage(personId);
                return NoContent();
            } catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }

        [Authorize]
        [HttpGet("profilepicture/{personId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<byte[]>> GetImageForPerson(int personId)
        {
            try
            {
                var result = await _service.GetImageForPerson(personId);

                if(result == null)
                    return NoContent();

                return Ok(result);
            }
            catch (Exception ex)
            {
                return NotFound(ex.Message);
            }
        }
    }
}
