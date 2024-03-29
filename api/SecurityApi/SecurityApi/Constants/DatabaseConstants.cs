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

        static public readonly int MAX_JOB_TITLE_LENGTH = 30;
        static public readonly int MAX_JOB_DESCRIPTION_LENGTH = 150;

        static public readonly int MIN_USER_PASSWORD_LENGTH = 8;
        static public readonly int MAX_USER_PASSWORD_LENGTH = 30;
        static public readonly int MAX_USER_FULLNAME_LENGTH = 30;

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
