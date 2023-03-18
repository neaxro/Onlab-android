namespace SecurityApi.Dtos;
public record UpdateShift(
    DateTime StartTime,
    DateTime EndTime,
    int StatusId,
    int WageId
    );
