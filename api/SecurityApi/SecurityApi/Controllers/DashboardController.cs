using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using SecurityApi.Services;
using Dashboard = SecurityApi.Dtos.Dashboard;

namespace SecurityApi.Controllers
{
    [Route("api/dashboard")]
    [ApiController]
    public class DashboardController : ControllerBase
    {
        private readonly IDashboardService _service;

        public DashboardController(IDashboardService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Dashboard>> GetAll()
        {
            var dboards = _service.ListAll();
            return Ok(dboards);
        }

        [HttpGet("{id}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<Dashboard>> GetById(int id)
        {
            var dboard = await _service.GetById(id);
            return dboard == null ? NotFound() : Ok(dboard);
        }

        [HttpGet("forperson/bycategoryid/{categoryId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<IEnumerable<Dashboard>>> GetForPersonByCategoryId(int categoryId)
        {
            IEnumerable<Dashboard> dboards = _service.ListForPersonByCategoryID(categoryId);
            return (dboards == null || dboards.Count() == 0) ? NotFound() : Ok(dboards);
        }

        [HttpGet("forperson/bypersonid/{personId}")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(StatusCodes.Status404NotFound)]
        public async Task<ActionResult<IEnumerable<Dashboard>>> GetForPersonByPersonId(int personId)
        {
            var dboards = await _service.ListForPersonByPersonID(personId);
            return (dboards == null || dboards.Count() == 0) ? NotFound() : Ok(dboards);
        }
    }
}
