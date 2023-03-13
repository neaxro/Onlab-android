using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class Role
{
    public int Id { get; set; }

    public string? Title { get; set; }

    public virtual ICollection<PeopleJob> PeopleJobs { get; } = new List<PeopleJob>();
}
