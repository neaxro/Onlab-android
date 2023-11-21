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
    public class ShiftServiceUnitTest
    {
        Mock<OnlabContext> mock;
        Mock<OnlabContext> dbContextMock;
        ShiftService shiftService, service;

        public ShiftServiceUnitTest()
        {
            mock = new Mock<OnlabContext>();
            dbContextMock = new Mock<OnlabContext>();
            shiftService = new ShiftService(mock.Object);
            service = new ShiftService(dbContextMock.Object);
        }

        // MethodName_StateUnderTest_ExpectedBehavior

        [Fact]
        public async void Get_GetShift_NotNull()
        {
            // Arrange
            int shiftId = 8;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);

            // Act
            var result = await shiftService.Get(shiftId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, shiftId);
        }

        [Fact]
        public void GetAll_GetAllShifts_NotEmpty()
        {
            // Arrange
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);

            // Act
            var result = shiftService.GetAll();

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
            Assert.Equal(result.Count(), shifts.Count);
        }

        [Fact]
        public void GetAllForJob_GetAllShiftsInJob_NotEmpty()
        {
            // Arrange
            int jobId = 2;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = shiftService.GetAllForJob(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void GetAllForJob_NotFoundJob_ThrowsException()
        {
            // Arrange
            int jobId = -1;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act & Assert
            Assert.Throws<Exception>(() => shiftService.GetAllForJob(jobId));
        }

        [Fact]
        public void GetAllForPerson_GetAllShiftsForPerson_NotEmpty()
        {
            // Arrange
            int personId = 2;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Person> people = DummyDatabase.GetPeople(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act
            var result = shiftService.GetAllForPerson(personId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void GetAllForPerson_PersonNotFound_ThrowsException()
        {
            // Arrange
            int personId = -1;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Person> people = DummyDatabase.GetPeople(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);

            // Act & Assert
            Assert.Throws<Exception>(() => shiftService.GetAllForPerson(personId));
        }

        [Fact]
        public void GetAllInProgress_GetAllInProgressShifts_NotEmpty()
        {
            // Arrange
            int jobId = 2;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);

            // Act
            var result = shiftService.GetAllInProgress(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void GetAllInProgress_JobNotFound_Empty()
        {
            // Arrange
            int jobId = -1;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);

            // Act
            var result = shiftService.GetAllInProgress(jobId);

            // Assert
            Assert.NotNull(result);
            Assert.Empty(result);
        }

        [Fact]
        public async void GetInProgressForPersonInJob_GetShifts_NotNull()
        {
            // Arrange
            int jobId = 2;
            int personId = 2;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);

            // Act
            var result = await shiftService.GetInProgressForPersonInJob(jobId, personId);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public async void GetInProgressForPersonInJob_NotFound_Null()
        {
            // Arrange
            int jobId = 1;
            int personId = 2;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);

            // Act
            var result = await shiftService.GetInProgressForPersonInJob(jobId, personId);

            // Assert
            Assert.Null(result);
        }

        [Fact]
        public void GetAcceptedShiftsForPersonInJob_GetShifts_NotNull()
        {
            // Arrange
            int jobId = 3;
            int personId = 3;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Person> people = DummyDatabase.GetPeople(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = shiftService.GetAcceptedShiftsForPersonInJob(jobId, personId);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public void GetAcceptedShiftsForPersonInJob_JobNotFound_ThrowsException()
        {
            // Arrange
            int jobId = -1;
            int personId = 3;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Person> people = DummyDatabase.GetPeople(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act & Assert
            Assert.Throws<Exception>(() => shiftService.GetAcceptedShiftsForPersonInJob(jobId, personId));
        }

        [Fact]
        public void GetAcceptedShiftsForPersonInJob_PersonNotFound_ThrowsException()
        {
            // Arrange
            int jobId = 9;
            int personId = -1;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Person> people = DummyDatabase.GetPeople(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act & Assert
            Assert.Throws<Exception>(() => shiftService.GetAcceptedShiftsForPersonInJob(jobId, personId));
        }

        [Fact]
        public void GetDeniedShiftsForPersonInJob_GetShifts_NotEmpty()
        {
            // Arrange
            int jobId = 4;
            int personId = 4;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Person> people = DummyDatabase.GetPeople(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = shiftService.GetDeniedShiftsForPersonInJob(jobId, personId);

            // Assert
            Assert.NotNull(result);
            Assert.NotEmpty(result);
        }

        [Fact]
        public void GetDeniedShiftsForPersonInJob_NoDeniedShifts_Empty()
        {
            // Arrange
            int jobId = 7;
            int personId = 5;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Person> people = DummyDatabase.GetPeople(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Person>>(c => c.People)
                .ReturnsDbSet(people);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = shiftService.GetDeniedShiftsForPersonInJob(jobId, personId);

            // Assert
            Assert.NotNull(result);
            Assert.Empty(result);
        }

        [Fact]
        public void GetJobStatistic_GetStatistics_NotNull()
        {
            // Arrange
            int jobId = 7;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act
            var result = shiftService.GetJobStatistic(jobId);

            // Assert
            Assert.NotNull(result);
        }

        [Fact]
        public void GetJobStatistic_JobNotFound_ThrowsException()
        {
            // Arrange
            int jobId = -7;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act & Assert
            Assert.Throws<Exception>(() => shiftService.GetJobStatistic(jobId));
        }

        [Fact]
        public async void Delete_ShiftDeleted_NotNull()
        {
            // Arrange
            int shiftId = 7;
            List<Shift> shifts = DummyDatabase.GetShifts(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);

            // Act
            var result = await shiftService.Delete(shiftId);

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Id, shiftId);
        }

        [Fact]
        public void Delete_ShiftNotFound_ThrowsException()
        {
            // Arrange
            int shiftId = -7;
            List<Shift> shifts = DummyDatabase.GetShifts(10);
            List<Job> jobs = DummyDatabase.GetJobs(10);

            mock.Setup<DbSet<Shift>>(c => c.Shifts)
                .ReturnsDbSet(shifts);
            mock.Setup<DbSet<Job>>(c => c.Jobs)
                .ReturnsDbSet(jobs);

            // Act & Assert
            Assert.ThrowsAsync<Exception>(() => shiftService.Delete(shiftId));
        }
    }
}
