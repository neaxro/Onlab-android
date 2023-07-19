using SecurityApi.Model;

namespace SecurityApi.Dtos.DashboardDtos;
public record Dashboard(
    int id,
    string Title,
    string Message,
    string CreationTime,
    string CreatorName,
    byte[]? CreatorProfilePicture,
    int GroupId,
    string GroupName
    );