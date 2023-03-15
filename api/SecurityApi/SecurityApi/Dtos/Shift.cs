namespace SecurityApi.Dtos;
public record Shift(
    int Id,
    DateTime? StartTime,
    DateTime? EndTime,
    float? EarnedMoney,
    Person Person,
    Job Job,
    State status,
    Wage Wage
    );
