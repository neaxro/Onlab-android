using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class Dashboard
{
    public int Id { get; set; }

    public string? Title { get; set; }

    public string? Message { get; set; }

    public DateTime? CreationTime { get; set; }

    public int? JobId { get; set; }

    public int? PeopleId { get; set; }

    public int? WageId { get; set; }

    public virtual Job? Job { get; set; }

    public virtual Person? People { get; set; }

    public virtual Wage? Wage { get; set; }
}
