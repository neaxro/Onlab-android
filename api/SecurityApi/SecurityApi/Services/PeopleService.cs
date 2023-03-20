﻿using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos;
using SecurityApi.Model;
using System.Data;
using System.Linq;
using Person = SecurityApi.Dtos.Person;

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

#warning Nem ellenoriz semmit, megteszi a valtoztatast (ezzel lehet 2 tulaj 1 munkaba)
        public async Task ChangePersonRole(int jobId, int personId, int roleId)
        {
            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var newRole = await _context.Roles.FirstOrDefaultAsync(r => r.Id == roleId);
            if (newRole == null)
            {
                await tran.RollbackAsync();
                throw new DataException(String.Format("Role with ID({0}) does not exist!", roleId));
            }

            var personConnection = await _context.PeopleJobs.FirstOrDefaultAsync(pj => pj.PeopleId == personId && pj.JobId == jobId);
            if(personConnection == null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Person with ID({0}) on Job ID({1}) does not exist!", personId, jobId));
            }

            personConnection.Role = newRole;

            await _context.SaveChangesAsync();
            await tran.CommitAsync();
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

        public IEnumerable<Person> GetAllOnJob(int jobId)
        {
            var people = _context.PeopleJobs
                .Where(pj => pj.JobId == jobId)
                .Include(pj => pj.People)
                .Select(pj => pj.People)
                .Select(_converter.ToModel)
                .ToList();

            return people;
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
