using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Dtos.ShiftDtos;

namespace SecurityApi.Dtos.JobDtos;
public record PersonSalaryStatistic(
    int PersonId,
    string PersonName,
    float FullSalary
    );
