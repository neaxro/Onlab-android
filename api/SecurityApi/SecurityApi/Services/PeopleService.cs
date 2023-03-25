using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Model;
using System.Data;
using System.Linq;
using Person = SecurityApi.Dtos.PersonDtos.Person;

namespace SecurityApi.Services
{
    public class PeopleService : IPeopleService
    {
        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;

        public PeopleService(OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
        }

        public async Task<Person> DeleteById(int id)
        {
            var result = await _context.People.FirstOrDefaultAsync(p => p.Id == id);

            if (result != null)
            {
                _context.People.Remove(result);
                await _context.SaveChangesAsync();
            }

            return result == null ? null : _converter.ToModel(result);
        }

        public async Task<Person> FindById(int id)
        {
            var result = await _context.People.FirstOrDefaultAsync(p => p.Id == id);
            return result == null ? null : _converter.ToModel(result);
        }

        public IEnumerable<Person> GetAll()
        {
            return _context.People.Select(_converter.ToModel).ToList();
            
        }

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

            return _converter.ToModel(person);
        }

        public async Task<Person> RemoveImage(int id)
        {
            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == id);

            if(person == null)
            {
                return null;
            }

            person.ProfilePicture = null;
            await _context.SaveChangesAsync();

            return person == null ? null : _converter.ToModel(person);
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

            return person == null ? null : _converter.ToModel(person);
        }

        public async Task<Person> UploadImage(int id, IFormFile image)
        {
            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == id);
            if(person != null)
            {
                using(var ms = new MemoryStream())
                {
                    image.CopyTo(ms);
                    person.ProfilePicture = ms.ToArray();
                }

                await _context.SaveChangesAsync();
            }

            return person == null ? null : _converter.ToModel(person);
        }
    }
}
