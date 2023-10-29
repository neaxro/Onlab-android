using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Moq;
using SecurityApi.Context;
using SecurityApi.Model;
using SecurityApi.Services;
using Xunit;

namespace SecurityApiUnitTests.Tests
{
    public class DashboardServiceTests
    {
        [Fact]
        public void passingTest()
        {
            Assert.Equal(1, 1);
        }
        
        private Mock<DbSet<Dashboard>> GetMock()
        {
            // Create a mock DbSet for the Dashboard entity
            var dashboardData = new List<Dashboard>
            {
                new Dashboard
                {
                    Id = 1,
                    Title = "Sample Dashboard 1",
                    Message = "Sample Message 1"
                },
                new Dashboard
                {
                    Id = 2,
                    Title = "Sample Dashboard 2",
                    Message = "Sample Message 2"
                }
            }.AsQueryable();

            var dashboardDbSetMock = new Mock<DbSet<Dashboard>>();
            dashboardDbSetMock.As<IQueryable<Dashboard>>().Setup(m => m.Provider).Returns(dashboardData.Provider);
            dashboardDbSetMock.As<IQueryable<Dashboard>>().Setup(m => m.Expression).Returns(dashboardData.Expression);
            dashboardDbSetMock.As<IQueryable<Dashboard>>().Setup(m => m.ElementType).Returns(dashboardData.ElementType);
            dashboardDbSetMock.As<IQueryable<Dashboard>>().Setup(m => m.GetEnumerator()).Returns(dashboardData.GetEnumerator());

            return dashboardDbSetMock;
        }
    }
}
