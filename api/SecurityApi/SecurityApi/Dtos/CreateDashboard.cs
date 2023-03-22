namespace SecurityApi.Dtos;
public record CreateDashboard(
    string Title,
    string Message,
    int JobId,
    int CreatorId,
    int GroupId
    );
