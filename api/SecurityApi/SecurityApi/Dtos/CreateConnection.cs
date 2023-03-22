namespace SecurityApi.Dtos;
public record CreateConnection(string Pin, int PersonId, int RoleId, int WageId);