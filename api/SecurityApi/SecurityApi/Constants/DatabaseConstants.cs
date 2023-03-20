namespace SecurityApi.Enums
{
    public class DatabaseConstants
    {
        static public readonly int ROLE_OWNER_ID = 1;
        static public readonly int ROLE_ADMIN_ID = 2;
        static public readonly int ROLE_USER_ID = 3;

        static public readonly int BROADCAST_MESSAGE_ID = 1;
        static public readonly int DEFAULT_WAGE_ID = 2;

        static public readonly int PENDING_STATUS_ID = 1;
        static public readonly int PROCESSING_STATUS_ID = 2;
        static public readonly int ACCEPTED_STATUS_ID = 4;
        static public readonly int DENY_STATUS_ID = 5;
    }
}
