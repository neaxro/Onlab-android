namespace SecurityApi.Dtos;
public record CreateDashboard(
    string Title,
    string Message,
    string JobName,
    string CreatorName,
    string GroupName
    );
