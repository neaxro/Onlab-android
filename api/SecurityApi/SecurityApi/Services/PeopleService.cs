using SecurityApi.Context;

namespace SecurityApi.Services
{
    public class PeopleService : IPeopleService
    {
        private readonly OnlabContext _context;

        public PeopleService(OnlabContext context)
        {
            _context = context;
        }

        public string probaUzenet()
        {
            return "Ez egy uzenet";
        }
    }
}
