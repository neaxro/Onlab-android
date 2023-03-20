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
            return new Dtos.Job(job.Id, job.Title, job.Description, owner);
        }
    }
}
