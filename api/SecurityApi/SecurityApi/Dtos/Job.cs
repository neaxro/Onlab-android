namespace SecurityApi.Dtos;
public record Job(
    int Id,
    string Title,
    string Pin,
    string Description,
    Person Owner
    );