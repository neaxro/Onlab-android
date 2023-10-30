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
    public class RoleServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        RoleService roleService, service;

        public RoleServiceUnitTest()
        {
            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            roleService = new RoleService(mock.Object);
            service = new RoleService(dbContextMock.Object);
        }

        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public async void Get_GetRole_NotNull()
        {
            // Arrange
            int roleId = 8;
            List<Role> roles = DummyDatabase.GetRoles(10);
            mock.Setup<DbSet<Role>>(c => c.Roles)
                .ReturnsDbSet(roles);

            // Act
            var result = await roleService.Get(roleId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, roleId);
        }

        [Fact]
        public async void Get_NotFound_ThrowsException()
        {
            // Arrange
            int roleId = -1;
            List<Role> roles = DummyDatabase.GetRoles(10);
            mock.Setup<DbSet<Role>>(c => c.Roles)
                .ReturnsDbSet(roles);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => roleService.Get(roleId));
        }

        [Fact]
        public void GetAll_GetAll_NotEmpty()
        {
            // Arrange
            List<Role> roles = DummyDatabase.GetRoles(10);
            mock.Setup<DbSet<Role>>(c => c.Roles)
                .ReturnsDbSet(roles);

            // Act
            var result = roleService.GetAll();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void GetAllChoosable_GetAllChoosable_NotEmpty()
        {
            // Arrange
            List<Role> roles = DummyDatabase.GetRoles(10);
            mock.Setup<DbSet<Role>>(c => c.Roles)
                .ReturnsDbSet(roles);

            // Act
            var result = roleService.GetAllChoosable();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
            Assert.True(result.Count() < roles.Count);
        }
    }
}
