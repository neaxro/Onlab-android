using Microsoft.EntityFrameworkCore;
using Moq;
using Moq.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Model;
using SecurityApi.Services;
using CreatePosition = SecurityApi.Dtos.PositionDtos.CreatePosition;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore.Scaffolding.Metadata;
using System.Data.Common;

namespace SecurityApiTest.Service_Tests
{
    public class PositionServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        PositionService positionService, service;

        public PositionServiceUnitTest()
        {
            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            positionService = new PositionService(mock.Object);
            service = new PositionService(dbContextMock.Object);
        }

        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public void GetPositions_GetTenPositions_True()
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

        [Fact]
        public async void Get_GetPosition_True()
        {
            // Arrange
            int positionId = 6;
            List<Position> positions = DummyDatabase.GetPositions(10);
            mock.Setup<DbSet<Position>>(c => c.Positions)
                .ReturnsDbSet(positions);

            // Act
            var result = await positionService.Get(positionId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, positionId);
        }

        [Fact]
        public void GetAllForPerson_GetPosition_NotEmpty()
        {
            // Arrange
            int jobId = 6;
            int personId = 6;
            List<Position> positions = DummyDatabase.GetPositions(10);
            mock.Setup<DbSet<Position>>(c => c.Positions)
                .ReturnsDbSet(positions);

            // Act
            var result = positionService.GetAllForPerson(jobId, personId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void GetAllForPerson_NoPerson_Empty()
        {
            // Arrange
            int jobId = 10;
            int personId = -2;
            List<Position> positions = DummyDatabase.GetPositions(10);
            mock.Setup<DbSet<Position>>(c => c.Positions)
                .ReturnsDbSet(positions);

            // Act
            var result = positionService.GetAllForPerson(jobId, personId);

            // Assert
            Assert.NotNull(result);
            Assert.Empty(result);
        }

        [Fact]
        public void GetAllLatestForAll_GetAllLatestPositions_NotEmpty()
        {
            // Arrange
            int jobId = 10;
            List<Position> positions = DummyDatabase.GetPositions(10);
            mock.Setup<DbSet<Position>>(c => c.Positions)
                .ReturnsDbSet(positions);

            // Act
            var result = positionService.GetAllLatestForAll(jobId);

            // Assert
            Assert.NotEmpty(result);
        }

        /*
        [Fact]
        public async void Create_PositionCreated_True()
        {
            // Arrange
            CreatePosition newPosition = new CreatePosition(20.19f, 11.23f);
            dbContextMock
                .Setup(x => x.Database.BeginTransaction())
                .Returns(new DbContextTransactionProxy());

            // Act
            var result = await positionService.Create(1, 2, newPosition);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Latitude, newPosition.Latitude);
            Assert.Equal(result.Longitude, newPosition.Longitude);
        }
        */

        [Fact]
        public async void Delete_PositionDeleted_True()
        {
            // Arrange
            int deletedPositionId = 1;
            List<Position> positions = DummyDatabase.GetPositions(10);
            mock.Setup<DbSet<Position>>(c => c.Positions)
                .ReturnsDbSet(positions);

            // Act
            var result = await positionService.Delete(deletedPositionId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, deletedPositionId);
        }
    }
}
