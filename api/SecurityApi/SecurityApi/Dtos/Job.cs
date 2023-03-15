namespace SecurityApi.Dtos;
public record Job(
    int Id,
    string Title,
    string Description,
    Person Owner
    );