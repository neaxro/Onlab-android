namespace SecurityApi.Dtos.JobDtos;

public record JobStatistic(
    float overallSalary,
    List<PersonSalaryStatistic> PeopleSalaryStatistics
    );