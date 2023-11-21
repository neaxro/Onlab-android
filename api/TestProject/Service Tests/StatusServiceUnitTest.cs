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
    public class StatusServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        StatusService statusService, service;

        public StatusServiceUnitTest()
        {
            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            statusService = new StatusService(mock.Object);
            service = new StatusService(dbContextMock.Object);
        }
        
        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public void GetAll_GetTenStatuses_NotEmpty()
        {
            // Arrange
            List<State> statuses = DummyDatabase.GetStates(10);
            mock.Setup<DbSet<State>>(c => c.States)
                .ReturnsDbSet(statuses);

            // Act
            var result = statusService.GetAll();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
            Assert.Equal(result.Count(), statuses.Count());
        }

        [Fact]
        public async void Get_GetStatus_NotNull()
        {
            // Arrange
            int statusId = 7;
            List<State> statuses = DummyDatabase.GetStates(10);
            mock.Setup<DbSet<State>>(c => c.States)
                .ReturnsDbSet(statuses);

            // Act
            var result = await statusService.Get(statusId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, statusId);
        }

        [Fact]
        public async void Get_NotFound_ThrowsException()
        {
            // Arrange
            int statusId = -1;
            List<State> statuses = DummyDatabase.GetStates(10);
            mock.Setup<DbSet<State>>(c => c.States)
                .ReturnsDbSet(statuses);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => statusService.Get(statusId));
        }
    }
}
