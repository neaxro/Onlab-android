namespace SecurityApi.Dtos;
public record Job(
    int Id,
    string Pin,
    string Title,
    string Description,
    Person Owner
    );