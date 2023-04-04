using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using SecurityApi.Context;
using SecurityApi.Converters;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Model;
using System.Data;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;
using System.Text.RegularExpressions;
using Person = SecurityApi.Dtos.PersonDtos.Person;

namespace SecurityApi.Services
{
    public class PeopleService : IPeopleService
    {
        private readonly OnlabContext _context;
        private readonly ModelToDtoConverter _converter;
        private readonly IConfiguration _configuration;

        public PeopleService(IConfiguration configuration, OnlabContext context)
        {
            _context = context;
            _converter = new ModelToDtoConverter();
            _configuration = configuration;
        }

        public async Task<Person> Delete(int personId)
        {
            var result = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);

            if(result == null)
            {
                throw new Exception(String.Format("Person with ID({0}) does not exist!", personId));
            }

            _context.People.Remove(result);
            await _context.SaveChangesAsync();

            return _converter.ToModel(result);
        }

        public async Task<Person> Get(int personId)
        {
            var result = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);

            if (result == null)
            {
                throw new Exception(String.Format("Person with ID({0}) does not exist!", personId));
            }

            return _converter.ToModel(result);
        }

        public IEnumerable<Person> GetAll()
        {
            var people = _context.People
                .Select(_converter.ToModel)
                .ToList();
            
            return people;
        }

        public async Task<string> Login(LoginPerson loginInformation)
        {
            var person = await _context.People.FirstOrDefaultAsync(p => p.Username == loginInformation.Username);
            if(person == null)
            {
                throw new Exception("Person does not exist!");
            }

            if(person.Password != loginInformation.Password)
            {
                throw new Exception("Incorrect Password!");
            }

            string token = CreateToken(person);
            return token;
        }

        private string CreateToken(Model.Person person)
        {
            List<Claim> claims = new List<Claim>
            {
                new Claim(ClaimTypes.SerialNumber, person?.Id.ToString()),
                new Claim(ClaimTypes.Name, person?.Name),
                new Claim(ClaimTypes.Email, person?.Email),
                new Claim(ClaimTypes.Surname, person?.Username)
            };


            var symmetricKey = new SymmetricSecurityKey(
                Encoding.UTF8.GetBytes(
                    _configuration.GetSection("SymmetricKeys:MySecretToken").Value!));

            var credentials = new SigningCredentials(symmetricKey, SecurityAlgorithms.HmacSha256);

            var token = new JwtSecurityToken(
                claims: claims,
                expires: DateTime.Now.AddDays(1),
                signingCredentials: credentials
                );

            var JsonWebToken = new JwtSecurityTokenHandler().WriteToken(token);

            return JsonWebToken;
        }

        public async Task<Person> Register(CreatePerson newPerson)
        {
            if (newPerson.Username.Any(Char.IsWhiteSpace)){
                throw new Exception("Username must not contain whitespaces!");
            }

            if (newPerson.Nickname.Any(Char.IsWhiteSpace)){
                throw new Exception("Nickname must not contain whitespaces!");
            }

            string justAlphabetPattern = @"^[a-zA-Z]+$";
            Match isValidNicname = Regex.Match(newPerson.Nickname, justAlphabetPattern, RegexOptions.IgnoreCase);
            if (!isValidNicname.Success)
            {
                throw new Exception("Nickname must contains only letters!");
            }

            string emailRegexPattern = @"^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$";
            Match isEmailValid = Regex.Match(newPerson.Email, emailRegexPattern, RegexOptions.IgnoreCase);
            if (!isEmailValid.Success)
            {
                throw new Exception("Email address invalid format!");
            }

            using var tran = await _context.Database.BeginTransactionAsync(IsolationLevel.RepeatableRead);

            var result = await _context.People.FirstOrDefaultAsync(p => p.Username.ToUpper() == newPerson.Username.ToUpper());
            
            if (result != null)
            {
                await tran.RollbackAsync();
                throw new Exception(String.Format("Username \"{0}\" has already taken!", newPerson.Username));
            }

            var person = new Model.Person()
            {
                Name = newPerson.FullName,
                Username = newPerson.Username,
                Nickname = newPerson.Nickname,
                Email = newPerson.Email,
                Password = newPerson.Password
            };

            await _context.People.AddAsync(person);
            await _context.SaveChangesAsync();

            await tran.CommitAsync();

            return _converter.ToModel(person);
        }

        public async Task<Person> RemoveImage(int personId)
        {
            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);

            if(person == null)
            {
                throw new Exception(String.Format("Person with ID({0}) does not exist!", personId));
            }

            person.ProfilePicture = null;
            await _context.SaveChangesAsync();

            return _converter.ToModel(person);
        }

        public async Task<Person> Update(int personId, CreatePerson newData)
        {
            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);

            if(person == null)
            {
                throw new Exception(String.Format("Person with ID({0}) does not exist!", personId));
            }

            var personWithNewUsername = await _context.People.FirstOrDefaultAsync(p => p.Username == newData.Username);
            if(personWithNewUsername != null)
            {
                // Not the same person who changes data but the username is same
                if(personWithNewUsername.Id != personId)
                {
                    throw new Exception("Username already taken!");
                }
                // Else the username is not change for the same user who is updateing itself
            }

            person.Name = newData.FullName;
            person.Username = newData.Username;
            person.Nickname = newData.Nickname;
            person.Email = newData.Email;
            person.Password = newData.Password;

            await _context.SaveChangesAsync();

            return _converter.ToModel(person);
        }

        public async Task<Person> UploadImage(int personId, IFormFile image)
        {
            var person = await _context.People.FirstOrDefaultAsync(p => p.Id == personId);

            if (person == null)
            {
                throw new Exception(String.Format("Person with ID({0}) does not exist!", personId));
            }

            using(var ms = new MemoryStream())
            {
                image.CopyTo(ms);
                person.ProfilePicture = ms.ToArray();
            }

            await _context.SaveChangesAsync();

            return _converter.ToModel(person);
        }
    }
}
