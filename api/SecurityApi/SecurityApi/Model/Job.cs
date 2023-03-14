using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class Job
{
    public int Id { get; set; }

    public string? Pin { get; set; }

    public string? Title { get; set; }

    public string? Description { get; set; }

    public int? PeopleId { get; set; }

    public virtual ICollection<Dashboard> Dashboards { get; } = new List<Dashboard>();

    public virtual Person? People { get; set; }

    public virtual ICollection<PeopleJob> PeopleJobs { get; } = new List<PeopleJob>();

    public virtual ICollection<Shift> Shifts { get; } = new List<Shift>();
}
