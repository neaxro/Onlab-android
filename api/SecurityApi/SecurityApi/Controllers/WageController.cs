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
    [Route("api/wage")]
    [ApiController]
    public class WageController : ControllerBase
    {
        private readonly IWageService _service;

        public WageController(IWageService service)
        {
            _service = service;
        }

        
    }
}
