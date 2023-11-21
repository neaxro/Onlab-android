using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;
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
using System.Net;
using System.Threading;
using System.Threading.Tasks;


namespace SecurityApiTest.Controller_Tests
{
    public class DashboardControllerUnitTest
    {
        Dashboard d1, d2, d3;
        List<Dashboard> dashboards;
        Mock<IDashboardService> mockService;
        DashboardController dashboardController;

        public DashboardControllerUnitTest()
        {
            d1 = new Dashboard(1,"Sample Dashboard 1","Sample Message 1","", "", null, 1, "");
            d2 = new Dashboard(2,"Sample Dashboard 2","Sample Message 2","", "", null, 2, "");
            d3 = new Dashboard(3,"Sample Dashboard 3","Sample Message 3","", "", null, 3, "");

            dashboards = new List<Dashboard> { d1, d2 };

            mockService = new Mock<IDashboardService>();
            dashboardController = new DashboardController ( mockService.Object );
        }

        [Fact]
        public void Get_AllDashboards_GetsAllDashboards()
        {
            // Arrange
            mockService.Setup(c => c.ListAll()).Returns(dashboards);

            // Act
            var result = (dashboardController.GetAll().Result as OkObjectResult).Value as IEnumerable<Dashboard>;

            // Assert
            Assert.Equal(result.Count(), dashboards.Count);
        }

        [Fact]
        public async void Get_GetDashboardById_GetsTheDashboard()
        {
            // Arrange
            int dashboardId = d1.id;
            mockService.Setup(c => c.GetById(dashboardId)).Returns(Task.FromResult(d1));

            // Act
            var result = await dashboardController.GetById(dashboardId);
            var r = (result.Result as OkObjectResult).Value as Dashboard;

            // Assert
            Assert.Equal(d1.id, r.id);
        }

        [Fact]
        public async void Insert_InsertNewDashboard_Succesfull()
        {
            // Arrange
            var newList = new List<Dashboard>();
            newList.AddRange(dashboards);
            newList.Add(d3);
            var newDashboard = new CreateDashboard(d3.Title, d3.Message, 1, 1, d3.GroupId);

            mockService.Setup(c => c.Insert(newDashboard)).Returns(Task.FromResult(d3));

            // Act
            var result = await dashboardController.InsertNewMessage(newDashboard);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public async void Delete_DeleteDashboard_Successfull()
        {
            // Arrange
            var newList = new List<Dashboard> { d1 };
            mockService.Setup(c => c.Delete(d2.id)).Returns(Task.FromResult(d2));

            // Act
            var result = await dashboardController.Remove(d2.id);

            // Assert
            Assert.NotNull(result);
            Assert.True(result.Result is NoContentResult);
        }
    }
}