using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class PeopleJob
{
    public int Id { get; set; }

    public int? JobId { get; set; }

    public int? PeopleId { get; set; }

    public int? RoleId { get; set; }

    public int? WageId { get; set; }

    public virtual Job? Job { get; set; }

    public virtual Person? People { get; set; }

    public virtual Role? Role { get; set; }

    public virtual Wage? Wage { get; set; }
}
