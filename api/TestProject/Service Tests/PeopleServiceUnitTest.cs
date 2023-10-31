using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Moq;
using Moq.EntityFrameworkCore;
using SecurityApi.Context;
using LoginPerson = SecurityApi.Dtos.PersonDtos.LoginPerson;
using CreatePerson = SecurityApi.Dtos.PersonDtos.CreatePerson;
using SecurityApi.Model;
using SecurityApi.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SecurityApiTest.Service_Tests
{
    public class PeopleServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        PeopleService peopleService, service;

        public PeopleServiceUnitTest()
        {
            Mock<IConfigurationSection> mockSection = new Mock<IConfigurationSection>();
            mockSection.Setup(s => s.Value).Returns("rohph1eewee9ox7Gothaehibaitheiph7koh4xikaeg3omaithohd9eisei0oLah");

            Mock<IConfiguration> mockConfiguration = new Mock<IConfiguration>();
            mockConfiguration.Setup(c => c.GetSection("SymmetricKeys:MySecretToken"))
                .Returns(mockSection.Object);

            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            peopleService = new PeopleService(mockConfiguration.Object, mock.Object);
            service = new PeopleService(mockConfiguration.Object, dbContextMock.Object);
        }

        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public async void Get_GetPersonById_NotNull()
        {
            // Arrange
            int personId = 8;
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act
            var result = await peopleService.Get(personId);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public async void Get_PersonNotFound_ThrowsException()
        {
            // Arrange
            int personId = -18;
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => peopleService.Get(personId));
        }

        [Fact]
        public void GetAll_GetAllPerson_NotEmpty()
        {
            // Arrange
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act
            var result = peopleService.GetAll();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void Login_Successfull_NotNull()
        {
            // Arrange
            string username = "Username 1";
            string password = "Password 1";
            LoginPerson loginData = new LoginPerson(username, password);
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act
            var result = peopleService.Login(loginData);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public void Login_IncorrectPassword_ThrowsException()
        {
            // Arrange
            string username = "Username 1";
            string password = "Wrong Password";
            LoginPerson loginData = new LoginPerson(username, password);
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => peopleService.Login(loginData));
        }

        [Fact]
        public void Login_PersonNotFound_ThrowsException()
        {
            // Arrange
            string username = "Teszt Elek";
            string password = "Password";
            LoginPerson loginData = new LoginPerson(username, password);
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => peopleService.Login(loginData));
        }

        [Fact]
        public async void RemoveImage_Successfull_NotNull()
        {
            // Arrange
            int personId = 3;
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act
            var result = await peopleService.RemoveImage(personId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, personId);
        }

        [Fact]
        public async void RemoveImage_PersonNotFound_ThrowsException()
        {
            // Arrange
            int personId = 3;
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => peopleService.RemoveImage(personId));
        }

        [Fact]
        public async void Update_Successfull_NotNull()
        {
            // Arrange
            int personId = 3;
            string fullName = "Teszt Elek";
            CreatePerson update = new CreatePerson(fullName, "", "", "", "");
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act
            var result = await peopleService.Update(personId, update);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, personId);
            Assert.Equal(result.FullName, fullName);
        }

        [Fact]
        public async void Update_PersonNotFound_ThrowsException()
        {
            // Arrange
            int personId = -23;
            CreatePerson update = new CreatePerson("", "", "", "", "");
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => peopleService.RemoveImage(personId));
        }

        [Fact]
        public async void Update_UsernameTaken_ThrowsException()
        {
            // Arrange
            int personId = 3;
            string username = "Username 1";
            CreatePerson update = new CreatePerson("", username, "", "", "");
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => peopleService.RemoveImage(personId));
        }

        [Fact]
        public async void GetImageForPerson_Successfull_Null()
        {
            // Arrange
            int personId = 3;
            List<Person> people = DummyDatabase.GetPeople(10);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act
            var result = await peopleService.GetImageForPerson(personId);

            // Assert
            Assert.Null(result);
        }
    }
}
