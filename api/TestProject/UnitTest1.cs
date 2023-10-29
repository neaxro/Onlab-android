using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Moq;
using SecurityApi.Context;
using SecurityApi.Controllers;
using SecurityApi.Converters;
using SecurityApi.Dtos;
using SecurityApi.Dtos.DashboardDtos;
using SecurityApi.Services;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;


namespace TestProject
{
    public class UnitTest1
    {
        Dashboard d1, d2, d3;
        List<Dashboard> dashboards;
        Mock<IDashboardService> mockService;
        DashboardController dashboardController;

        public UnitTest1()
        {
            d1 = new Dashboard(1,"Sample Dashboard 1","Sample Message 1","", "", null, 1, "");
            d2 = new Dashboard(2,"Sample Dashboard 2","Sample Message 2","", "", null, 2, "");
            d3 = new Dashboard(3,"Sample Dashboard 3","Sample Message 3","", "", null, 3, "");

            dashboards = new List<Dashboard> { d1, d2, d3 };

            mockService = new Mock<IDashboardService>();
            dashboardController = new DashboardController ( mockService.Object );
        }

        [Fact]
        public void Get_AllDashboards_GetsAllDashboards()
        {
            // Arrange
            mockService.Setup(s => s.ListAll()).Returns(dashboards);

            // Act
            var result = (dashboardController.GetAll().Result as OkObjectResult).Value as IEnumerable<Dashboard>;

            // Assert
            Assert.Equal(result.Count(), dashboards.Count);
        }
    }
}