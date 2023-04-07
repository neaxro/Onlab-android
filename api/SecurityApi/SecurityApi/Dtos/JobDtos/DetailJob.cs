using SecurityApi.Dtos.PersonDtos;

namespace SecurityApi.Dtos.JobDtos;
public record DetailJob(
    int Id,
    string Title,
    string Pin,
    string Description,
    Person Owner
    );