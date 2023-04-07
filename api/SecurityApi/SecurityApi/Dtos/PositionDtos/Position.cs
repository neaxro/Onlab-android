using SecurityApi.Dtos.JobDtos;
using SecurityApi.Dtos.PersonDtos;

namespace SecurityApi.Dtos.PositionDtos;
public record Position(int Id, DateTime? Time, float? Longitude, float? Latitude, Person Person, DetailJob Job);