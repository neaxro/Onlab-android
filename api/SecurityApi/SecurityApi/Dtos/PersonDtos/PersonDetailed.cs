using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Dtos.RoleDtos;
using SecurityApi.Dtos.WageDtos;

namespace SecurityApi.Dtos;
public record PersonDetailed(Person BasicInfo, Wage Wage, Role Role);
