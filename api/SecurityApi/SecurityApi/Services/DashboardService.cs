using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos;
using SecurityApi.Enums;
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
        private readonly ModelToDtoConverter _converter;

        public DashboardService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
        }

        public async Task<Dashboard> Delete(int id)
        {
            var dboard = await _context.Dashboards
                .Include(d => d.Wage)
                .Include(d => d.Job)
                .Include(d => d.People)
                .FirstOrDefaultAsync(d => d.Id == id);
            
            if(dboard != null)
            {
                _context.Dashboards.Remove(dboard);
                await _context.SaveChangesAsync();
            }

            return dboard == null ? null : _converter.ToModel(dboard);
        }

        public async Task<Dashboard> GetById(int id)
        {
            var dboard = await _context.Dashboards
                .Where(d => d.Id == id)
                .Include(d => d.Wage)
                .Include(d => d.People)
                .FirstOrDefaultAsync();

            return dboard == null ? null : _converter.ToModel(dboard);
        }

        public async Task<Dashboard> Insert(CreateDashboard dashboard)
        {
            Model.Dashboard newMessage = null;
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var wage = await _context.Wages.FirstOrDefaultAsync(w => w.Id == dashboard.GroupId);
            var creator = await _context.People.FirstOrDefaultAsync(p => p.Id == dashboard.CreatorId);
            var job = await _context.Jobs.FirstOrDefaultAsync(j => j.Id == dashboard.JobId);

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

            return newMessage == null ? null : _converter.ToModel(newMessage);
        }

        public IEnumerable<Dashboard> ListAll()
        {
            var dboards = _context.Dashboards
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Select(_converter.ToModel)
                .ToList();

            return dboards;
        }

        public IEnumerable<Dashboard> ListAllInJob(int jobId)
        {
            var dashboardes = _context.Dashboards
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Where(d => d.JobId == jobId)
                .Select(_converter.ToModel)
                .ToList();

            return dashboardes;
        }

        public IEnumerable<Dashboard> ListForPersonByCategoryID(int jobId, int categoryId)
        {
            int broadcastWageId = DatabaseConstants.GetBroadcastWageID(jobId, _context);

            var dboards = _context.Dashboards
                .Where(d => d.JobId == jobId && (d.WageId == categoryId || d.WageId == broadcastWageId))
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Select(_converter.ToModel)
                .ToList();

            return dboards;
        }

        public async Task<IEnumerable<Dashboard>> ListForPersonByPersonID(int jobId, int personId)
        {
            var person = await _context.People.SingleOrDefaultAsync(p => p.Id == personId);
            
            if (person == null)
                return null;

            int broadcastWageId = DatabaseConstants.GetBroadcastWageID(jobId, _context);

            var dboards = _context.Dashboards
                .Where(d => d.JobId == jobId && (d.PeopleId == person.Id || d.WageId == broadcastWageId))
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Select(_converter.ToModel)
                .ToList();

            return dboards;
        }

        public async Task<Dashboard> Update(int id, CreateDashboard newContent)
        {
            using var tarn = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            var dboard = await _context.Dashboards
                .Include(d => d.Wage)
                .Include(d => d.People)
                .Include(d => d.Job)
                .FirstOrDefaultAsync(d => d.Id == id);

            // Not found
            if (dboard == null)
                return null;

            var group = await _context.Wages.FirstOrDefaultAsync(w => w.Id == newContent.GroupId);

            dboard.Title = newContent.Title;
            dboard.Message = newContent.Message;

            if(group != null)
            {
                dboard.Wage = group;
            }

            await _context.SaveChangesAsync();
            await tarn.CommitAsync();

            return _converter.ToModel(dboard);
        }
    }
}
