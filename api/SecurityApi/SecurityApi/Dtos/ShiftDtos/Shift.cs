using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PersonDtos;
using SecurityApi.Dtos.StatusDtos;
using SecurityApi.Dtos.WageDtos;

namespace SecurityApi.Dtos.ShiftDtos;
public record Shift(
    int Id,
    DateTime? StartTime,
    DateTime? EndTime,
    float? EarnedMoney,
    Person Person,
    Job Job,
    Status status,
    Wage Wage
    );
