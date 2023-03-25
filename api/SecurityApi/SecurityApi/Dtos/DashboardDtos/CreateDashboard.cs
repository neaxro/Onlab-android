namespace SecurityApi.Dtos.DashboardDtos;
public record CreateDashboard(
    string Title,
    string Message,
    int JobId,
    int CreatorId,
    int GroupId
    );
