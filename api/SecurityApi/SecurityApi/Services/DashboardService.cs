using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using System.Security.Cryptography;
using Dashboard = SecurityApi.Dtos.Dashboard;


namespace SecurityApi.Services
{
    public class DashboardService : IDashboardService
    {
        private readonly OnlabContext _context;
        public DashboardService(OnlabContext context)
        {
            _context = context;
        }
        public async Task<Dashboard> GetById(int id)
        {
            throw new NotImplementedException();
        }

        public Task Insert(Dashboard dashboard)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Dashboard> ListAll()
        {
            var dboards = _context.Dashboards
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Select(ToModel)
                .ToList();
            return dboards;
        }

        public Task<Dashboard> ListForPerson(int personId)
        {
            throw new NotImplementedException();
        }

        public Task Update(int id)
        {
            throw new NotImplementedException();
        }

        private Dashboard ToModel(Model.Dashboard dashboard)
        {
            return new Dashboard(
                dashboard.Title,
                dashboard.Message,
                dashboard.CreationTime,
                dashboard.People.Name,
                dashboard.People.ProfilePicture,
                dashboard.Wage.Id,
                dashboard.Wage.Name
                );
        }
    }
}
