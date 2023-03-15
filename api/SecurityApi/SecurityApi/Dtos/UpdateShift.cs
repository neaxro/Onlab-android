namespace SecurityApi.Dtos;
public record UpdateShift(
    DateTime? StartTime,
    DateTime? EndTime,
    State status,
    Wage Wage
    );
