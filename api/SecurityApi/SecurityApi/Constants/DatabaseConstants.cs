﻿using Microsoft.EntityFrameworkCore;
using SecurityApi.Context;
using SecurityApi.Services;

namespace SecurityApi.Enums
{
    public class DatabaseConstants
    {
        static public readonly int ROLE_OWNER_ID = 1;
        static public readonly int ROLE_ADMIN_ID = 2;
        static public readonly int ROLE_USER_ID = 3;

        static public readonly int PENDING_STATUS_ID = 1;
        static public readonly int PROCESSING_STATUS_ID = 2;
        static public readonly int ACCEPTED_STATUS_ID = 3;
        static public readonly int DENY_STATUS_ID = 4;

        // If the Wage does not exist in the Job it will throw an Exception!
        static public int GetBroadcastWageID(int jobId, OnlabContext context)
        {
            var id = context.Wages
                .Where(w => w.JobId == jobId)
                .Min(w => w.Id);

            return id;
        }

        // If the Wage does not exist in the Job it will throw an Exception!
        static public int GetDefaultWageID(int jobId, OnlabContext context)
        {
            return GetBroadcastWageID(jobId, context) + 1;
        }
    }
}
