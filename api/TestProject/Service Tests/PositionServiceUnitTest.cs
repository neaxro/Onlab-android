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
    public class PositionServiceUnitTest
    {
        Mock<OnlabContext> mock;
        PositionService positionService;

        public PositionServiceUnitTest()
        {
            mock = new Mock<OnlabContext>();
            positionService = new PositionService(mock.Object);
        }

        [Fact]
        public void test()
        {
            // Arrange
            List<Position> positions = DummyDatabase.GetPositions(10);
            mock.Setup<DbSet<Position>>(c => c.Positions)
                .ReturnsDbSet(positions);

            // Act
            var result = positionService.GetAll();

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Count(), positions.Count());
        }
    }
}
