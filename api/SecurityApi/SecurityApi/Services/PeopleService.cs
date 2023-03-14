using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Dtos;
using SecurityApi.Model;
using System.Data;

namespace SecurityApi.Services
{
    public class PeopleService : IPeopleService
    {
        private readonly OnlabContext _context;

        public PeopleService(OnlabContext context)
        {
            _context = context;
        }

        public Person FindById(int id)
        {
            var talalat = _context.People.FirstOrDefault(p => p.Id == id);
            return talalat == null ? null : talalat;
        }

        public Person Insert(CreatePerson newPerson)
        {
            using var tran = _context.Database.BeginTransaction(IsolationLevel.RepeatableRead);

            if(_context.People.Any(p => EF.Functions.Like(p.Username, newPerson.Username)))
            {
                throw new Exception("Felhasználó már létezik!");
            }

            var person = new Person()
            {
                Name = newPerson.FullName,
                Username = newPerson.Username,
                Nickname = newPerson.Nickname,
                Email = newPerson.Email,
                Password = newPerson.Password
            };

            _context.People.Add(person);
            _context.SaveChanges();

            tran.Commit();

            return person;
        }
    }
}
