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
    }
}
