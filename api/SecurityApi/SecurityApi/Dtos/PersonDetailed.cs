namespace SecurityApi.Dtos;

public record PersonDetailed(Person BasicInfo, Wage Wage, Role Role);
