using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
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

        [HttpGet]
        public async Task<ActionResult<string>> ListAll()
        {
            string uzi = _service.probaUzenet();

            return Ok(uzi);
        }
    }
}
