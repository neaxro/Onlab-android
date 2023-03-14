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

        [HttpGet("{id}")]
        public async Task<ActionResult<Person>> Get(int id)
        {
            var talalat = _service.FindById(id);
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
        public async Task<ActionResult<Person>> Insert([FromBody] CreatePerson uj)
        {
            try
            {
                var created = _service.Insert(uj);
                return CreatedAtAction(nameof(Get), new { id = created.Id }, created);
            }
            catch (ArgumentException ex)
            {
                ModelState.AddModelError(nameof(CreatePerson.FullName), ex.Message);
                return ValidationProblem(ModelState);
            }
        }
    }
}
