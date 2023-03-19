namespace SecurityApi.Dtos;
public record Position(int Id, DateTime Time, float Longitude, float Latitude, Person person, Job job);