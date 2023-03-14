using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using System.Data;
using Person = SecurityApi.Dtos.Person;

namespace SecurityApi.Services
{
    public class PeopleService : IPeopleService
    {
        private readonly OnlabContext _context;

        public PeopleService(OnlabContext context)
        {
            _context = context;
        }

        public async Task<Person> DeleteById(int id)
        {
            var result = await _context.People.FirstOrDefaultAsync(p => p.Id == id);

            if (result != null)
            {
                _context.People.Remove(result);
                await _context.SaveChangesAsync();
            }

            return result == null ? null : ToModel(result);
        }

        public async Task<Person> FindById(int id)
        {
            var result = await _context.People.FirstOrDefaultAsync(p => p.Id == id);
            return result == null ? null : ToModel(result);
        }

        public IEnumerable<Person> GetAll()
        {
            return _context.People.Select(ToModel).ToList();
            
        }

        /*
         * Ez majd masik kontollerbe
        public IEnumerable<PersonDetailed> GetAllOnJob(int jobId)
        {
            var people = _context.PeopleJobs
                .Where(pj => pj.JobId == jobId)
                .Include(pj => pj.Wage)
                .Include(pj => pj.People)
                .Include(pj => pj.Role)
                .ToList();

            throw new NotImplementedException();
        }*/

        public async Task<Person> Insert(CreatePerson newPerson)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);
            var result = await _context.People.AnyAsync(p => EF.Functions.Like(p.Username, newPerson.Username));
            
            if (result)
            {
                throw new Exception("User already exists!");
            }

            var person = new Model.Person()
            {
                Name = newPerson.FullName,
                Username = newPerson.Username,
                Nickname = newPerson.Nickname,
                Email = newPerson.Email
            };

            await _context.People.AddAsync(person);
            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            return ToModel(person);
        }

        public async Task<Person> Update(int id, CreatePerson newData)
        {
            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == id);

            if(person != null)
            {
                person.Name = newData.FullName;
                person.Username = newData.Username;
                person.Nickname = newData.Nickname;
                person.Email = newData.Email;
                person.Password = newData.Password;

                await _context.SaveChangesAsync();
            }

            return person == null ? null : ToModel(person);
        }

        public Task UploadImage(byte[] image)
        {
            throw new NotImplementedException();
        }

        private Person ToModel(Model.Person person)
        {
            return new Person(
                person.Id,
                person.Name,
                person.Username,
                person.Nickname,
                person.Email,
                person.ProfilePicture
                );
        }
    }
}
