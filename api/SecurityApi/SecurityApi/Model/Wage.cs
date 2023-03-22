using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class Wage
{
    public int Id { get; set; }

    public string? Name { get; set; }

    public float? Price { get; set; }

    public int? JobId { get; set; }

    public virtual ICollection<Dashboard> Dashboards { get; } = new List<Dashboard>();

    public virtual Job? Job { get; set; }

    public virtual ICollection<PeopleJob> PeopleJobs { get; } = new List<PeopleJob>();

    public virtual ICollection<Shift> Shifts { get; } = new List<Shift>();
}
