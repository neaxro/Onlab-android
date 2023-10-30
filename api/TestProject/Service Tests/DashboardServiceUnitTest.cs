using Microsoft.EntityFrameworkCore;
using Moq;
using Moq.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Model;
using SecurityApi.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SecurityApiTest.Service_Tests
{
    public class DashboardServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        DashboardService dashboardService, service;

        public DashboardServiceUnitTest()
        {
            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            dashboardService = new DashboardService(mock.Object);
            service = new DashboardService(dbContextMock.Object);
        }

        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public void ListAll_GetTenDashboards_NotEmpty()
        {
            // Arrange
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);

            // Act
            var result = dashboardService.ListAll();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
            Assert.Equal(result.Count(), dashboards.Count);
        }

        [Fact]
        public void ListAllInJob_GetDashboardsInJob_NotEmpty()
        {
            // Arrange
            int jobId = 1;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);

            // Act
            var result = dashboardService.ListAllInJob(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void ListForPersonByCategoryID_GetDashboards_NotEmpty()
        {
            // Arrange
            int jobId = 2;
            int categoryId = 2;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            List<Wage> wages = DummyDatabase.GetWages(10);

            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);
            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);

            // Act
            var result = dashboardService.ListForPersonByCategoryID(jobId, categoryId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public async void ListForPersonByPersonID_GetDashboards_NotEmpty()
        {
            // Arrange
            int jobId = 6;
            int personId = 6;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            List<Wage> wages = DummyDatabase.GetWages(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);
            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act
            var result = await dashboardService.ListForPersonByPersonID(jobId, personId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public async void ListForPersonByPersonID_PersonNotFound_ThrowsException()
        {
            // Arrange
            int jobId = 9;
            int personId = 7;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            List<Wage> wages = DummyDatabase.GetWages(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);
            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => dashboardService.ListForPersonByPersonID(jobId, personId));
        }

        [Fact]
        public async void GetById_GetDashboard_NotNull()
        {
            // Arrange
            int dashboardId = 4;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);

            // Act
            var result = await dashboardService.GetById(dashboardId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.id, dashboardId);
        }

        [Fact]
        public async void GetById_NotFound_ThrowsException()
        {
            // Arrange
            int dashboardId = -2;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => dashboardService.GetById(dashboardId));
        }

        [Fact]
        public async void Delete_DashboardDeleted_NotNull()
        {
            // Arrange
            int dashboardId = 4;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);

            // Act
            var result = await dashboardService.Delete(dashboardId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.id, dashboardId);
        }

        [Fact]
        public async void Delete_DashboardNotFound_ThrowsException()
        {
            // Arrange
            int dashboardId = -1;
            List<Dashboard> dashboards = DummyDatabase.GetDashboards(10);
            mock.Setup<DbSet<Dashboard>>(c => c.Dashboards)
                .ReturnsDbSet(dashboards);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => dashboardService.Delete(dashboardId));
        }
    }
}
