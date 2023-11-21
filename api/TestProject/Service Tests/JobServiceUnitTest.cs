using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Moq;
using Moq.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos.PeopleJobDtos;
using SecurityApi.Model;
using SecurityApi.Services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SecurityApiTest.Service_Tests
{
    public class JobServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        JobService jobService, service;

        public JobServiceUnitTest()
        {
            Mock<IConfigurationSection> mockSection = new Mock<IConfigurationSection>();
            mockSection.Setup(s => s.Value).Returns("rohph1eewee9ox7Gothaehibaitheiph7koh4xikaeg3omaithohd9eisei0oLah");

            Mock<IConfiguration> mockConfiguration = new Mock<IConfiguration>();
            mockConfiguration.Setup(c => c.GetSection("SymmetricKeys:MySecretToken"))
                .Returns(mockSection.Object);

            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            jobService = new JobService(mockConfiguration.Object, mock.Object);
            service = new JobService(mockConfiguration.Object, dbContextMock.Object);
        }

        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public async void Get_GetJobById_NotNull()
        {
            // Arrange
            int jobId = 8;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = await jobService.Get(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, jobId);
        }

        [Fact]
        public async void Get_JobNotFound_ThrowsException()
        {
            // Arrange
            int jobId = -1;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            Assert.ThrowsAsync<Exception>(() => jobService.Get(jobId));
        }

        [Fact]
        public void GetAll_GetAllJob_NotEmpty()
        {
            // Arrange
            List<Job> jobs = DummyDatabase.GetJobs(10);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = jobService.GetAll();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void GetAllAvailableForPerson_GetAllForPerson_NotEmpty()
        {
            // Arrange
            int personId = 1;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);
            
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act
            var result = jobService.GetAllAvailableForPerson(personId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public async void GetConnection_GetPin_NotNull()
        {
            // Arrange
            int connectionId = 5;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act
            var result = await jobService.GetConnection(connectionId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, connectionId);
        }

        [Fact]
        public async void GetConnection_NotFound_ThrowsException()
        {
            // Arrange
            int connectionId = -5;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => jobService.GetConnection(connectionId));
        }

        [Fact]
        public async void GetDetailed_GetDetailedJobById_NotNull()
        {
            // Arrange
            int jobId = 8;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = await jobService.GetDetailed(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, jobId);
        }

        [Fact]
        public async void GetDetailed_JobNotFound_ThrowsException()
        {
            // Arrange
            int jobId = -8;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => jobService.GetDetailed(jobId));
        }

        [Fact]
        public async void GetPersonDetailsInJob_GetPerson_NotNull()
        {
            // Arrange
            int jobId = 5;
            int personId = 5;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act
            var result = await jobService.GetPersonDetailsInJob(jobId, personId);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public async void GetPersonDetailsInJob_PersonNotFound_ThrowsException()
        {
            // Arrange
            int jobId = -5;
            int personId = 15;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => jobService.GetPersonDetailsInJob(jobId, personId));
        }

        [Fact]
        public async void SelectJob_JobSelected_NotNull()
        {
            // Arrange
            int jobId = 6;
            int personId = 6;
            SelectJob selectJob = new SelectJob(jobId, personId);
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act
            var result = await jobService.SelectJob(selectJob);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public async void SelectJob_JobNotFound_ThrowsException()
        {
            // Arrange
            int jobId = -6;
            int personId = 16;
            SelectJob selectJob = new SelectJob(jobId, personId);
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => jobService.SelectJob(selectJob));
        }

        [Fact]
        public void AllPersonInJob_GetAllPersonInJob_NotEmpty()
        {
            // Arrange
            int jobId = 6;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act
            var result = jobService.AllPersonInJob(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void AllPersonInJob_JobNotFound_Empty()
        {
            // Arrange
            int jobId = -16;
            List<Job> jobs = DummyDatabase.GetJobs(10);
            List<PeopleJob> peopleJobs = DummyDatabase.GetPeopleJobs(10);

            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);
            mock.Setup<DbSet<PeopleJob>>(c => c.PeopleJobs)
                .ReturnsDbSet(peopleJobs);

            // Act
            var result = jobService.AllPersonInJob(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.Empty(result);
        }
    }
}
