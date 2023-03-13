using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class Person
{
    public int Id { get; set; }

    public string? Name { get; set; }

    public string? Username { get; set; }

    public string? Nickname { get; set; }

    public string? Email { get; set; }

    public string? Password { get; set; }

    public string? ProfilePicture { get; set; }

    public virtual ICollection<Dashboard> Dashboards { get; } = new List<Dashboard>();

    public virtual ICollection<Job> Jobs { get; } = new List<Job>();

    public virtual ICollection<PeopleJob> PeopleJobs { get; } = new List<PeopleJob>();

    public virtual ICollection<Position> Positions { get; } = new List<Position>();

    public virtual ICollection<Shift> Shifts { get; } = new List<Shift>();
}
