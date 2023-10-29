using Microsoft.AspNetCore.Http.HttpResults;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Moq;
using Newtonsoft.Json.Linq;
using NuGet.Protocol;
using SecurityApi.Context;
using SecurityApi.Controllers;
using SecurityApi.Converters;
using SecurityApi.Dtos;
using SecurityApi.Dtos.DashboardDtos;
using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PeopleJobDtos;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Enums;
using SecurityApi.Services;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading;
using System.Threading.Tasks;

namespace SecurityApiTest
{
    public class JobUnitTest
    {
        DetailJob dj1, dj2;
        Person owner;
        Job job1, job2;
        List<DetailJob> detailedJobList;
        List<Job> jobList;
        Mock<IJobService> jobServiceMock;
        JobController jobController;

        public JobUnitTest()
        {
            owner = new Person(1, "Teszt Elek", "tesztelek", "elek", "testelek@unit.test", new byte[] { 1, 1, 1 } );
            
            dj1 = new DetailJob(1, "Sample Title 1", "123456", "Sample Description 1", owner);
            dj2 = new DetailJob(2, "Sample Title 2", "654321", "Sample Description 2", owner);
            detailedJobList = new List<DetailJob>() { dj1, dj2 };

            job1 = new Job(1, "Sample Title 1", "Sample Description 1");
            job2 = new Job(2, "Sample Title 2", "Sample Description 2");
            jobList = new List<Job>() { job1, job2 };

            jobServiceMock = new Mock<IJobService>();
            jobController = new JobController(jobServiceMock.Object);
        }

        [Fact]
        public void Get_Alljobs_GetAll()
        {
            // Arrange
            jobServiceMock.Setup(c => c.GetAll()).Returns(detailedJobList);

            // Act
            var result = (jobController.GetAllJobs().Result as OkObjectResult).Value as IEnumerable<DetailJob>;

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Count(), detailedJobList.Count);
        }

        [Fact]
        public async void Get_ById_GetTheJob()
        {
            // Arrange
            jobServiceMock.Setup(c => c.Get(job1.Id)).Returns(Task.FromResult(job1));

            // Act
            var result = await jobController.Get(job1.Id);
            var r = (result.Result as OkObjectResult).Value as Job;
            
            // Assert
            Assert.NotNull(result);
            Assert.Equal(r.Id, job1.Id);
        }

        [Fact]
        public async void Get_ByIdDetailed_GetTheDetailedJob()
        {
            // Arrange
            jobServiceMock.Setup(c => c.GetDetailed(dj1.Id)).Returns(Task.FromResult(dj1));

            // Act
            var result = await jobController.GetDetailed(dj1.Id);
            var r = (result.Result as OkObjectResult).Value as DetailJob;
            
            // Assert
            Assert.NotNull(result);
            Assert.Equal(r.Id, dj1.Id);
        }

        [Fact]
        public void Get_GetAllAvailableForPerson_GetAllForPerson()
        {
            // Arrange
            jobServiceMock.Setup(c => c.GetAllAvailableForPerson(owner.Id)).Returns(detailedJobList);

            // Act
            var result = (jobController.GetAllAvailableForPerson(owner.Id).Result as OkObjectResult).Value as IEnumerable<DetailJob>;

            // Assert
            Assert.NotNull(result);
            Assert.Equal(result.Count(), detailedJobList.Count);
        }

        [Fact]
        public async void Get_Connection_GetTheConnection()
        {
            // Arrange
            var selectJob = new SelectJob(dj2.Id, owner.Id);
            string connectionToken = "Sample Connection Token";
            jobServiceMock.Setup(c => c.SelectJob(selectJob)).Returns(Task.FromResult(connectionToken));

            // Act
            var result = await jobController.SelectJob(selectJob);
            var r = (result.Result as OkObjectResult).Value;
            var token = r.GetType().GetProperty("Token").GetValue(r, null) as string;

            // Assert
            Assert.NotNull(result);
            Assert.Equal(token, connectionToken);
        }

        [Fact]
        public async void Post_ConnectPersonToJob_Successfull()
        {
            // Arrange
            var connectionPin = dj2.Pin;
            var personId = 3;
            var personJob = new PersonJob(1, dj2.Title, "Winch Eszter");

            jobServiceMock.Setup(c => c.ConnectToJob(connectionPin, personId, DatabaseConstants.ROLE_USER_ID)).Returns(Task.FromResult(personJob));

            // Act
            var result = await jobController.ConncetPersonToJob(connectionPin, personId);
            var r = (result.Result as CreatedAtActionResult).Value as PersonJob;

            // Assert
            Assert.NotNull(result);
            Assert.Equal(r.JobName, dj2.Title);
        }
    }
}
