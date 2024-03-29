﻿using SecurityApi.Dtos.DashboardDtos;

namespace SecurityApi.Services
{
    public interface IDashboardService
    {
        IEnumerable<Dashboard> ListAll();
        Task<Dashboard> GetById(int dashboardId);
        IEnumerable<Dashboard> ListForPersonByCategoryID(int jobId, int categoryId);
        Task<IEnumerable<Dashboard>> ListForPersonByPersonID(int jobId, int personId);
        IEnumerable<Dashboard> ListAllInJob(int jobId);
        Task<Dashboard> Insert(CreateDashboard dashboard);
        Task<Dashboard> Update(int dashboardId, UpdateDashboard newContent);
        Task<Dashboard> Delete(int dashboardId);
    }
}
