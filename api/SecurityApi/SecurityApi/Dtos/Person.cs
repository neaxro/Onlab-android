namespace SecurityApi.Dtos;

public record Person(
    int Id,
    string FullName,
    string Username,
    string Nickname,
    string Email,
    byte[] ProfilePicture
    );
