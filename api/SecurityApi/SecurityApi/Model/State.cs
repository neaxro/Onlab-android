using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class State
{
    public int Id { get; set; }

    public string? Title { get; set; }

    public string? Description { get; set; }

    public virtual ICollection<Shift> Shifts { get; } = new List<Shift>();
}
