using SecurityApi.Dtos.PersonDtos;

namespace SecurityApi.Dtos.JobDtos;
public record Job(
    int Id,
    string Title,
    string Pin,
    string Description,
    Person Owner
    );