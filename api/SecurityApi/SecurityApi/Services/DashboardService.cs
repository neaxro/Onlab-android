using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using System.Data;
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

        public async Task<Dashboard> Insert(CreateDashboard dashboard)
        {
            Model.Dashboard newMessage = null;
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Name == dashboard.GroupName);
            var creator = await _context.People.FirstOrDefaultAsync(p => p.Name == dashboard.CreatorName);
            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Title == dashboard.JobName);

            if(wage != null && creator != null && job != null)
            {
                newMessage = new Model.Dashboard()
                {
                    Title = dashboard.Title,
                    Message = dashboard.Message,
                    Job = job,
                    People = creator,
                    Wage = wage
                };

                await _context.Dashboards.AddAsync(newMessage);
                await _context.SaveChangesAsync();
            }

            await tran.CommitAsync();

            return newMessage == null ? null : ToModel(newMessage);
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

        public Task<Dashboard> Update(int id)
        {
            throw new NotImplementedException();
        }

        private Dashboard ToModel(Model.Dashboard dashboard)
        {
            return new Dashboard(
                dashboard.Id,
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
