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

        public async Task<Person> FindById(int id)
        {
            var result = await _context.People.FirstOrDefaultAsync(p => p.Id == id);
            return result == null ? null : ToModel(result);
        }

        public IEnumerable<Person> GetAll()
        {
            return _context.People.Select(ToModel).ToList();
            
        }

        public async Task<Person> Insert(CreatePerson newPerson)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);
            var result = await _context.People.AnyAsync(p => EF.Functions.Like(p.Username, newPerson.Username));
            
            if (result)
            {
                throw new Exception("User already exists!");
            }

            var person = new DbPerson()
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

        public Task<Person> Update(CreatePerson newData)
        {
            throw new NotImplementedException();
        }

        private Person ToModel(DbPerson person)
        {
            Person p = new Person(
                person.Id,
                person.Name,
                person.Username,
                person.Nickname,
                person.Email,
                person.ProfilePicture
                );

            return p;
        }
    }
}
