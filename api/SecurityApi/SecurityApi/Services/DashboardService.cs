using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using System.Runtime.InteropServices;
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
            var dboard = await _context.Dashboards
                .Where(d => d.Id == id)
                .Include(d => d.Wage)
                .Include(d => d.People)
                .FirstOrDefaultAsync();

            return dboard == null ? null : ToModel(dboard);
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

        public IEnumerable<Dashboard> ListForPersonByCategoryID(int categoryId)
        {
            const int BROADCAST_MESSAGE_ID = 1;

            var dboards = _context.Dashboards
                .Where(d => d.WageId == categoryId || d.WageId == BROADCAST_MESSAGE_ID)
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Select(ToModel)
                .ToList();

            return dboards;
        }

        public async Task<IEnumerable<Dashboard>> ListForPersonByPersonID(int personId)
        {
            const int BROADCAST_MESSAGE_ID = 1;

            var person = await _context.People.SingleOrDefaultAsync(p => p.Id == personId);
            
            if (person == null)
                return null;

            var dboards = _context.Dashboards
                .Where(d => d.PeopleId == person.Id || d.WageId == BROADCAST_MESSAGE_ID)
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Select(ToModel)
                .ToList();

            return dboards;
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
