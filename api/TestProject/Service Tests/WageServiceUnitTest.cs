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
    public class WageServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        WageService wageService, service;

        public WageServiceUnitTest()
        {
            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            wageService = new WageService(mock.Object);
            service = new WageService(dbContextMock.Object);
        }

        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public void GetAll_GetAllWages_NotEmpty()
        {
            // Arrange
            List<Wage> wages = DummyDatabase.GetWages(10);
            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);

            // Act
            var result = wageService.GetAll();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
            Assert.Equal(result.Count(), wages.Count);
        }

        [Fact]
        public async void GetById_GetWage_NotNull()
        {
            // Arrange
            int wageId = 3;
            List<Wage> wages = DummyDatabase.GetWages(10);
            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);

            // Act
            var result = await wageService.GetById(wageId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, wageId);
        }

        [Fact]
        public async void GetById_NotFound_TrowsException()
        {
            // Arrange
            int wageId = -1;
            List<Wage> wages = DummyDatabase.GetWages(10);
            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => wageService.GetById(wageId));
        }

        [Fact]
        public async void GetMessageCategories_GetAll_NotEmpty()
        {
            // Arrange
            int jobId = 1;

            List<Wage> wages = DummyDatabase.GetWages(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = await wageService.GetMessageCategories(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public async void GetWagesInJob_NotGetAll_Empty()
        {
            // Arrange
            int jobId = 1;

            List<Wage> wages = DummyDatabase.GetWages(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = await wageService.GetWagesInJob(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.Empty(result);
        }

        [Fact]
        public async void Delete_NoPermission_ThrowsException()
        {
            // Arrange
            int wageId = 2;

            List<Wage> wages = DummyDatabase.GetWages(10);

            mock.Setup<DbSet<Wage>>(c => c.Wages)
                .ReturnsDbSet(wages);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => wageService.Delete(wageId));
        }
    }
}
