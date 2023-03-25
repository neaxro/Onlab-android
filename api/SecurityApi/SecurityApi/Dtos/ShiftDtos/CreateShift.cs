namespace SecurityApi.Dtos.ShiftDtos;
public record CreateShift(
    int JobId,
    int PersonId,
    int WageId
    );
