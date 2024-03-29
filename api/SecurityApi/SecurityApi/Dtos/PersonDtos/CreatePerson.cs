﻿using Microsoft.Build.Framework;

namespace SecurityApi.Dtos.PersonDtos;

public record CreatePerson(
    string FullName,
    string Username,
    string Nickname,
    string Email,
    string Password
    );
