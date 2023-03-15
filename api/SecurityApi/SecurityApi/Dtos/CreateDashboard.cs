namespace SecurityApi.Dtos;
public record CreateDashboard(
    string Title,
    string Message,
    string JobName,
    int CreatorId,
    string GroupName
    );
