namespace SecurityApi.Dtos.ShiftDtos;
public record UpdateShift(
    DateTime StartTime,
    DateTime EndTime,
    int StatusId,
    int WageId
    );
