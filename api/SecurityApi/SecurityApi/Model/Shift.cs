using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class Shift
{
    public int Id { get; set; }

    public DateTime? StartTime { get; set; }

    public DateTime? EndTime { get; set; }

    public float? EarnedMoney { get; set; }

    public int? PeopleId { get; set; }

    public int? JobId { get; set; }

    public int? WageId { get; set; }

    public int? StatusId { get; set; }

    public virtual Job? Job { get; set; }

    public virtual Person? People { get; set; }

    public virtual State? Status { get; set; }

    public virtual Wage? Wage { get; set; }
}
