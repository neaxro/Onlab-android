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

        public Dtos.Person ToModel(Model.Person person)
        {
            return new Dtos.Person(person.Id, person.Name, person.Username, person.Nickname, person.Email, person.ProfilePicture);
        }

        public Dtos.Job ToModel(Model.Job job)
        {
            Dtos.Person owner = ToModel(job.People);
            return new Dtos.Job(job.Id, job.Title, job.Pin, job.Description, owner);
        }

        public Dtos.Dashboard ToModel(Model.Dashboard dashboard)
        {
            return new Dtos.Dashboard(dashboard.Id, dashboard.Title, dashboard.Message, dashboard.CreationTime, dashboard.People.Name, dashboard.People.ProfilePicture, dashboard.Wage.Id, dashboard.Wage.Name);
        }

        public Dtos.Position ToModel(Model.Position position)
        {
            Dtos.Person positionedPerson = ToModel(position.People);
            Dtos.Job job = ToModel(position.Job);

            return new Dtos.Position(position.Id, position.Time, position.Longitude, position.Latitude, positionedPerson, job);
        }

        public Dtos.Wage ToModel(Model.Wage wage)
        {
            return new Dtos.Wage(wage.Id, wage.Name, wage.Price);
        }

        public Dtos.State ToModel(Model.State state)
        {
            return new Dtos.State(state.Id, state.Title, state.Description);
        }

        public Dtos.Shift ToModel(Model.Shift shift)
        {
            Dtos.Person person = ToModel(shift.People);
            Dtos.Job job = ToModel(shift.Job);
            Dtos.State status = ToModel(shift.Status);
            Dtos.Wage wage = ToModel(shift.Wage);

            return new Dtos.Shift(shift.Id, shift.StartTime, shift.EndTime, shift.EarnedMoney, person, job, status, wage);
        }
    }
}
