using System;
using System.Collections.Generic;

namespace SecurityApi.Model;

public partial class Position
{
    public int Id { get; set; }

    public DateTime? Time { get; set; }

    public float? Longitude { get; set; }

    public float? Latitude { get; set; }

    public int? PeopleId { get; set; }

    public virtual Person? People { get; set; }
}
