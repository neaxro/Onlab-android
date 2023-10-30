using SecurityApi.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SecurityApiTest
{
    public static class DummyDatabase
    {
        public static List<Dashboard> GetDashboards(int count)
        {
            var dashboards = new List<Dashboard>();

            for (int i = 1; i <= count; i++)
            {
                var dashboard = new Dashboard
                {
                    Id = i,
                    Title = $"Dashboard {i}",
                    Message = $"Message {i}",
                    CreationTime = DateTime.Now.AddDays(-i),
                    JobId = i,
                    PeopleId = i,
                    WageId = i,
                    Job = GetRandomJob(i),
                    People = GetRandomPerson(i),
                    Wage = GetRandomWage(i)
                };

                dashboards.Add(dashboard);
            }

            return dashboards;
        }

        public static List<Job> GetJobs(int count)
        {
            var jobs = new List<Job>();

            for (int i = 1; i <= count; i++)
            {
                var job = new Job
                {
                    Id = i,
                    Pin = $"Pin {i}",
                    Title = $"Job {i}",
                    Description = $"Description {i}",
                    PeopleId = i,
                    People = GetRandomPerson(i),
                };

                jobs.Add(job);
            }

            return jobs;
        }

        public static List<PeopleJob> GetPeopleJobs(int count)
        {
            var peopleJobs = new List<PeopleJob>();

            for (int i = 1; i <= count; i++)
            {
                var peopleJob = new PeopleJob
                {
                    Id = i,
                    JobId = i,
                    PeopleId = i,
                    RoleId = i,
                    WageId = i,
                    Job = GetRandomJob(i),
                    People = GetRandomPerson(i),
                    Role = GetRandomRole(i),
                    Wage = GetRandomWage(i)
                };

                peopleJobs.Add(peopleJob);
            }

            return peopleJobs;
        }

        public static List<Person> GetPeople(int count)
        {
            var people = new List<Person>();

            for (int i = 1; i <= count; i++)
            {
                var person = new Person
                {
                    Id = i,
                    Name = $"Person {i}",
                    Username = $"Username {i}",
                    Nickname = $"Nickname {i}",
                    Email = $"Email {i}",
                    Password = $"Password {i}"
                };

                people.Add(person);
            }

            return people;
        }

        public static List<Position> GetPositions(int count)
        {
            var positions = new List<Position>();

            for (int i = 1; i <= count; i++)
            {
                var position = new Position
                {
                    Id = i,
                    Time = DateTime.Now.AddHours(-i),
                    Longitude = (float)(i * 1.1),
                    Latitude = (float)(i * 0.9),
                    PeopleId = i,
                    JobId = i,
                    Job = GetRandomJob(i),
                    People = GetRandomPerson(i)
                };

                positions.Add(position);
            }

            return positions;
        }

        public static List<Role> GetRoles(int count)
        {
            var roles = new List<Role>();

            for (int i = 1; i <= count; i++)
            {
                var role = new Role
                {
                    Id = i,
                    Title = $"Role {i}"
                };

                roles.Add(role);
            }

            return roles;
        }

        public static List<Shift> GetShifts(int count)
        {
            var shifts = new List<Shift>();

            for (int i = 1; i <= count; i++)
            {
                var shift = new Shift
                {
                    Id = i,
                    StartTime = DateTime.Now.AddHours(-i),
                    EndTime = DateTime.Now,
                    EarnedMoney = (float)(i * 2.5),
                    PeopleId = i,
                    JobId = i,
                    WageId = i,
                    StatusId = i,
                    Job = GetRandomJob(i),
                    People = GetRandomPerson(i),
                    Status = GetRandomState(i),
                    Wage = GetRandomWage(i)
                };

                shifts.Add(shift);
            }

            return shifts;
        }

        public static List<State> GetStates(int count)
        {
            var states = new List<State>();

            for (int i = 1; i <= count; i++)
            {
                var state = new State
                {
                    Id = i,
                    Title = $"State {i}",
                    Description = $"Description {i}"
                };

                states.Add(state);
            }

            return states;
        }

        public static List<Wage> GetWages(int count)
        {
            var wages = new List<Wage>();

            for (int i = 1; i <= count; i++)
            {
                var wage = new Wage
                {
                    Id = i,
                    Name = $"Wage {i}",
                    Price = (float)(i * 10.0),
                    JobId = i
                };

                wages.Add(wage);
            }

            return wages;
        }

        // Helper methods to get random objects
        public static Job GetRandomJob(int id) => GetJobs(id).FirstOrDefault(j => j.Id == id);
        public static Person GetRandomPerson(int id) => GetPeople(id).FirstOrDefault(p => p.Id == id);
        public static Role GetRandomRole(int id) => GetRoles(id).FirstOrDefault(r => r.Id == id);
        public static Wage GetRandomWage(int id) => GetWages(id).FirstOrDefault(w => w.Id == id);
        public static State GetRandomState(int id) => GetStates(id).FirstOrDefault(s => s.Id == id);
        public static Position GetRandomPosition(int id) => GetPositions(id).FirstOrDefault(p => p.Id == id);
    }
}
