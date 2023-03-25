using SecurityApi.Dtos.DashboardDtos;
using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PeopleJobDtos;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Dtos.PositionDtos;
using SecurityApi.Dtos.ShiftDtos;
using SecurityApi.Dtos.StateDtos;
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

        public Job ToModel(Model.Job job)
        {
            Person owner = ToModel(job.People);
            return new Dtos.JobDtos.Job(job.Id, job.Title, job.Pin, job.Description, owner);
        }

        public Dashboard ToModel(Model.Dashboard dashboard)
        {
            return new Dtos.DashboardDtos.Dashboard(dashboard.Id, dashboard.Title, dashboard.Message, dashboard.CreationTime, dashboard.People.Name, dashboard.People.ProfilePicture, dashboard.Wage.Id, dashboard.Wage.Name);
        }

        public Position ToModel(Model.Position position)
        {
            Person positionedPerson = ToModel(position.People);
            Job job = ToModel(position.Job);

            return new Dtos.PositionDtos.Position(position.Id, position.Time, position.Longitude, position.Latitude, positionedPerson, job);
        }

        public Wage ToModel(Model.Wage wage)
        {
            return new Dtos.WageDtos.Wage(wage.Id, wage.Name, wage.Price, wage.Job.Title);
        }

        public State ToModel(Model.State state)
        {
            return new Dtos.StateDtos.State(state.Id, state.Title, state.Description);
        }

        public Shift ToModel(Model.Shift shift)
        {
            Person person = ToModel(shift.People);
            Job job = ToModel(shift.Job);
            State status = ToModel(shift.Status);
            Wage wage = ToModel(shift.Wage);

            return new Dtos.ShiftDtos.Shift(shift.Id, shift.StartTime, shift.EndTime, shift.EarnedMoney, person, job, status, wage);
        }

        public PersonJob ToModel(Model.PeopleJob peopleJob)
        {
            return new Dtos.PeopleJobDtos.PersonJob(peopleJob.Id, peopleJob.Job.Title, peopleJob.People.Name);
        }

        public MessageCategory WageToMessageCategory(Model.Wage wage)
        {
            return new Dtos.WageDtos.MessageCategory(wage.Id, wage.Name);
        }
    }
}
