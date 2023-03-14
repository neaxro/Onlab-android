using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SecurityApi.Migrations
{
    /// <inheritdoc />
    public partial class PersonImageType : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__JobID__1BC821DD",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__Peopl__1CBC4616",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__WageI__1DB06A4F",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Jobs__PeopleID__0F624AF8",
                table: "Jobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__JobID__2645B050",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__Peopl__2739D489",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__RoleI__282DF8C2",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__WageI__29221CFB",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__Positions__Peopl__236943A5",
                table: "Positions");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__JobID__160F4887",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__PeopleID__151B244E",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__StatusID__17F790F9",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__WageID__17036CC0",
                table: "Shifts");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Wages__3214EC27E1C7D797",
                table: "Wages");

            migrationBuilder.DropPrimaryKey(
                name: "PK__States__3214EC27FCF937CA",
                table: "States");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Shifts__3214EC27EF801669",
                table: "Shifts");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Roles__3214EC2775EA9E73",
                table: "Roles");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Position__3214EC27A8B08031",
                table: "Positions");

            migrationBuilder.DropPrimaryKey(
                name: "PK__PeopleJo__3214EC2702AC13E0",
                table: "PeopleJobs");

            migrationBuilder.DropPrimaryKey(
                name: "PK__People__3214EC272E84FF20",
                table: "People");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Jobs__3214EC27DAAB7DC5",
                table: "Jobs");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Dashboar__3214EC27EB0A7CCD",
                table: "Dashboard");

            migrationBuilder.RenameIndex(
                name: "UQ__Wages__737584F6CC418525",
                table: "Wages",
                newName: "UQ__Wages__737584F690805AF8");

            migrationBuilder.RenameIndex(
                name: "UQ__States__2CB664DCE6DF2B2F",
                table: "States",
                newName: "UQ__States__2CB664DCD995C264");

            migrationBuilder.RenameIndex(
                name: "UQ__Roles__2CB664DC94E0B49A",
                table: "Roles",
                newName: "UQ__Roles__2CB664DCF49F7188");

            migrationBuilder.RenameIndex(
                name: "UQ__People__536C85E4477C00D6",
                table: "People",
                newName: "UQ__People__536C85E43DDCEEC5");

            migrationBuilder.RenameIndex(
                name: "UQ__Jobs__2CB664DC052710DB",
                table: "Jobs",
                newName: "UQ__Jobs__2CB664DCA4025C8F");

            migrationBuilder.AlterColumn<byte[]>(
                name: "ProfilePicture",
                table: "People",
                type: "image",
                nullable: true,
                oldClrType: typeof(string),
                oldType: "text",
                oldNullable: true);

            migrationBuilder.AddPrimaryKey(
                name: "PK__Wages__3214EC2743945115",
                table: "Wages",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__States__3214EC27BBD22DBD",
                table: "States",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Shifts__3214EC2799175283",
                table: "Shifts",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Roles__3214EC27328BC743",
                table: "Roles",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Position__3214EC27BE98AA82",
                table: "Positions",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__PeopleJo__3214EC270AC63FE6",
                table: "PeopleJobs",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__People__3214EC27A6BA1D9E",
                table: "People",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Jobs__3214EC27433AE975",
                table: "Jobs",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Dashboar__3214EC2724CE1DDD",
                table: "Dashboard",
                column: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__JobID__41EDCAC5",
                table: "Dashboard",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__Peopl__42E1EEFE",
                table: "Dashboard",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__WageI__43D61337",
                table: "Dashboard",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Jobs__PeopleID__3587F3E0",
                table: "Jobs",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__JobID__4C6B5938",
                table: "PeopleJobs",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__Peopl__4D5F7D71",
                table: "PeopleJobs",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__RoleI__4E53A1AA",
                table: "PeopleJobs",
                column: "RoleID",
                principalTable: "Roles",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__WageI__4F47C5E3",
                table: "PeopleJobs",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Positions__Peopl__498EEC8D",
                table: "Positions",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__JobID__3C34F16F",
                table: "Shifts",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__PeopleID__3B40CD36",
                table: "Shifts",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__StatusID__3E1D39E1",
                table: "Shifts",
                column: "StatusID",
                principalTable: "States",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__WageID__3D2915A8",
                table: "Shifts",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__JobID__41EDCAC5",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__Peopl__42E1EEFE",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__WageI__43D61337",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Jobs__PeopleID__3587F3E0",
                table: "Jobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__JobID__4C6B5938",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__Peopl__4D5F7D71",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__RoleI__4E53A1AA",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__WageI__4F47C5E3",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__Positions__Peopl__498EEC8D",
                table: "Positions");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__JobID__3C34F16F",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__PeopleID__3B40CD36",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__StatusID__3E1D39E1",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__WageID__3D2915A8",
                table: "Shifts");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Wages__3214EC2743945115",
                table: "Wages");

            migrationBuilder.DropPrimaryKey(
                name: "PK__States__3214EC27BBD22DBD",
                table: "States");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Shifts__3214EC2799175283",
                table: "Shifts");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Roles__3214EC27328BC743",
                table: "Roles");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Position__3214EC27BE98AA82",
                table: "Positions");

            migrationBuilder.DropPrimaryKey(
                name: "PK__PeopleJo__3214EC270AC63FE6",
                table: "PeopleJobs");

            migrationBuilder.DropPrimaryKey(
                name: "PK__People__3214EC27A6BA1D9E",
                table: "People");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Jobs__3214EC27433AE975",
                table: "Jobs");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Dashboar__3214EC2724CE1DDD",
                table: "Dashboard");

            migrationBuilder.RenameIndex(
                name: "UQ__Wages__737584F690805AF8",
                table: "Wages",
                newName: "UQ__Wages__737584F6CC418525");

            migrationBuilder.RenameIndex(
                name: "UQ__States__2CB664DCD995C264",
                table: "States",
                newName: "UQ__States__2CB664DCE6DF2B2F");

            migrationBuilder.RenameIndex(
                name: "UQ__Roles__2CB664DCF49F7188",
                table: "Roles",
                newName: "UQ__Roles__2CB664DC94E0B49A");

            migrationBuilder.RenameIndex(
                name: "UQ__People__536C85E43DDCEEC5",
                table: "People",
                newName: "UQ__People__536C85E4477C00D6");

            migrationBuilder.RenameIndex(
                name: "UQ__Jobs__2CB664DCA4025C8F",
                table: "Jobs",
                newName: "UQ__Jobs__2CB664DC052710DB");

            migrationBuilder.AlterColumn<string>(
                name: "ProfilePicture",
                table: "People",
                type: "text",
                nullable: true,
                oldClrType: typeof(byte[]),
                oldType: "image",
                oldNullable: true);

            migrationBuilder.AddPrimaryKey(
                name: "PK__Wages__3214EC27E1C7D797",
                table: "Wages",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__States__3214EC27FCF937CA",
                table: "States",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Shifts__3214EC27EF801669",
                table: "Shifts",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Roles__3214EC2775EA9E73",
                table: "Roles",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Position__3214EC27A8B08031",
                table: "Positions",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__PeopleJo__3214EC2702AC13E0",
                table: "PeopleJobs",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__People__3214EC272E84FF20",
                table: "People",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Jobs__3214EC27DAAB7DC5",
                table: "Jobs",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Dashboar__3214EC27EB0A7CCD",
                table: "Dashboard",
                column: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__JobID__1BC821DD",
                table: "Dashboard",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__Peopl__1CBC4616",
                table: "Dashboard",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__WageI__1DB06A4F",
                table: "Dashboard",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Jobs__PeopleID__0F624AF8",
                table: "Jobs",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__JobID__2645B050",
                table: "PeopleJobs",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__Peopl__2739D489",
                table: "PeopleJobs",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__RoleI__282DF8C2",
                table: "PeopleJobs",
                column: "RoleID",
                principalTable: "Roles",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__WageI__29221CFB",
                table: "PeopleJobs",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Positions__Peopl__236943A5",
                table: "Positions",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__JobID__160F4887",
                table: "Shifts",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__PeopleID__151B244E",
                table: "Shifts",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__StatusID__17F790F9",
                table: "Shifts",
                column: "StatusID",
                principalTable: "States",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__WageID__17036CC0",
                table: "Shifts",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");
        }
    }
}
