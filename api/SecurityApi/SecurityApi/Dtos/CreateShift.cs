namespace SecurityApi.Dtos;
public record CreateShift(
    int PersonId,
    int JobId,
    int WageId
    );
