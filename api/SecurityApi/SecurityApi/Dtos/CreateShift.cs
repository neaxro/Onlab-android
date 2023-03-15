namespace SecurityApi.Dtos;
public record CreateShift(
    Person Person,
    Job Job,
    State Status,
    Wage Wage
    );
