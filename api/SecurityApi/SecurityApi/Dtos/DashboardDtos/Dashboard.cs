using SecurityApi.Model;

namespace SecurityApi.Dtos.DashboardDtos;
public record Dashboard(
    int id,
    string Title,
    string Message,
    DateTime? CreationTime,
    string CreatorName,
    byte[]? CreatorProfilePicture,
    int GroupId,
    string GroupName
    );