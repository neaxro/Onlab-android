using NuGet.Protocol;
using SecurityApi.Dtos;
using SecurityApi.Dtos.DashboardDtos;
using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PeopleJobDtos;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Dtos.PositionDtos;
using SecurityApi.Dtos.RoleDtos;
using SecurityApi.Dtos.ShiftDtos;
using SecurityApi.Dtos.StatusDtos;
using SecurityApi.Dtos.WageDtos;

namespace SecurityApi.Converters
{
    public class ModelToDtoConverter
    {
        private static ModelToDtoConverter converter = null;
        private static readonly object lockObject = new object();

        public ModelToDtoConverter() { }

        public static ModelToDtoConverter Instance
        {
            get
            {
                lock(lockObject)
                {
                    if (converter == null)
                    {
                        converter = new ModelToDtoConverter();
                    }

                    return converter;
                }
            }
        }

        public Person ToModel(Model.Person person)
        {
            return new Dtos.PersonDtos.Person(person.Id, person.Name, person.Username, person.Nickname, person.Email, person.ProfilePicture);
        }

        public PersonDetailed ToDetailedModel(Model.PeopleJob person)
        {
            Person p = ToModel(person.People);
            Wage w = ToModel(person.Wage);
            Role r = ToModel(person.Role);

            return new Dtos.PersonDetailed(p, w, r);
        }

        public DetailJob ToDetailedModel(Model.Job job)
        {
            Person owner = ToModel(job.People);
            return new Dtos.JobDtos.DetailJob(job.Id, job.Title, job.Pin, job.Description, owner);
        }

        public Job ToModel(Model.Job job)
        {
            return new Dtos.JobDtos.Job(job.Id, job.Title, job.Description);
        }

        public Dashboard ToModel(Model.Dashboard dashboard)
        {
            return new Dtos.DashboardDtos.Dashboard(dashboard.Id, dashboard.Title, dashboard.Message, dashboard.CreationTime.Value.ToString("MMM d H:m"), dashboard.People.Name, dashboard.People.ProfilePicture, dashboard.Wage.Id, dashboard.Wage.Name);
        }

        public Position ToModel(Model.Position position)
        {
            Person positionedPerson = ToModel(position.People);
            DetailJob job = ToDetailedModel(position.Job);

            return new Dtos.PositionDtos.Position(position.Id, position.Time, position.Longitude, position.Latitude, positionedPerson, job);
        }

        public Wage ToModel(Model.Wage wage)
        {
            return new Dtos.WageDtos.Wage(wage.Id, wage.Name, wage.Price, wage.Job.Title);
        }

        public Status ToModel(Model.State state)
        {
            return new Dtos.StatusDtos.Status(state.Id, state.Title, state.Description);
        }

        public Shift ToModel(Model.Shift shift)
        {
            Person person = ToModel(shift.People);
            DetailJob job = ToDetailedModel(shift.Job);
            Status status = ToModel(shift.Status);
            Wage wage = ToModel(shift.Wage);

            return new Dtos.ShiftDtos.Shift(shift.Id, shift.StartTime, shift.EndTime, shift.EarnedMoney, person, job, status, wage);
        }

        public PersonJob ToModel(Model.PeopleJob peopleJob)
        {
            return new Dtos.PeopleJobDtos.PersonJob(peopleJob.Id, peopleJob.Job.Title, peopleJob.People.Name);
        }

        public Role ToModel(Model.Role role)
        {
            return new Dtos.RoleDtos.Role(role.Id, role.Title);
        }

        public MessageCategory WageToMessageCategory(Model.Wage wage)
        {
            return new Dtos.WageDtos.MessageCategory(wage.Id, wage.Name);
        }
    }
}
