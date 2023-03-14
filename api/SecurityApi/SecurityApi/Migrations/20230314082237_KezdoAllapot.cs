using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SecurityApi.Migrations
{
    /// <inheritdoc />
    public partial class KezdoAllapot : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "People",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(type: "nvarchar(30)", maxLength: 30, nullable: true),
                    Username = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    Nickname = table.Column<string>(type: "nvarchar(30)", maxLength: 30, nullable: true),
                    Email = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    Password = table.Column<string>(type: "nvarchar(30)", maxLength: 30, nullable: true),
                    ProfilePicture = table.Column<string>(type: "text", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__People__3214EC272E84FF20", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "Roles",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Title = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__Roles__3214EC2775EA9E73", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "States",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Title = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    Description = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__States__3214EC27FCF937CA", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "Wages",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Name = table.Column<string>(type: "nvarchar(30)", maxLength: 30, nullable: true),
                    Wage = table.Column<float>(type: "real", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__Wages__3214EC27E1C7D797", x => x.ID);
                });

            migrationBuilder.CreateTable(
                name: "Jobs",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Pin = table.Column<string>(type: "varchar(6)", unicode: false, maxLength: 6, nullable: true),
                    Title = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    Description = table.Column<string>(type: "nvarchar(100)", maxLength: 100, nullable: true),
                    PeopleID = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__Jobs__3214EC27DAAB7DC5", x => x.ID);
                    table.ForeignKey(
                        name: "FK__Jobs__PeopleID__0F624AF8",
                        column: x => x.PeopleID,
                        principalTable: "People",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateTable(
                name: "Positions",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Time = table.Column<DateTime>(type: "datetime2", nullable: true),
                    Longitude = table.Column<float>(type: "real", nullable: true),
                    Latitude = table.Column<float>(type: "real", nullable: true),
                    PeopleID = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__Position__3214EC27A8B08031", x => x.ID);
                    table.ForeignKey(
                        name: "FK__Positions__Peopl__236943A5",
                        column: x => x.PeopleID,
                        principalTable: "People",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateTable(
                name: "Dashboard",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    Title = table.Column<string>(type: "nvarchar(20)", maxLength: 20, nullable: true),
                    Message = table.Column<string>(type: "text", nullable: true),
                    CreationTime = table.Column<DateTime>(type: "datetime2", nullable: true, defaultValueSql: "(getdate())"),
                    JobID = table.Column<int>(type: "int", nullable: true),
                    PeopleID = table.Column<int>(type: "int", nullable: true),
                    WageID = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__Dashboar__3214EC27EB0A7CCD", x => x.ID);
                    table.ForeignKey(
                        name: "FK__Dashboard__JobID__1BC821DD",
                        column: x => x.JobID,
                        principalTable: "Jobs",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__Dashboard__Peopl__1CBC4616",
                        column: x => x.PeopleID,
                        principalTable: "People",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__Dashboard__WageI__1DB06A4F",
                        column: x => x.WageID,
                        principalTable: "Wages",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateTable(
                name: "PeopleJobs",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    JobID = table.Column<int>(type: "int", nullable: true),
                    PeopleID = table.Column<int>(type: "int", nullable: true),
                    RoleID = table.Column<int>(type: "int", nullable: true),
                    WageID = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__PeopleJo__3214EC2702AC13E0", x => x.ID);
                    table.ForeignKey(
                        name: "FK__PeopleJob__JobID__2645B050",
                        column: x => x.JobID,
                        principalTable: "Jobs",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__PeopleJob__Peopl__2739D489",
                        column: x => x.PeopleID,
                        principalTable: "People",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__PeopleJob__RoleI__282DF8C2",
                        column: x => x.RoleID,
                        principalTable: "Roles",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__PeopleJob__WageI__29221CFB",
                        column: x => x.WageID,
                        principalTable: "Wages",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateTable(
                name: "Shifts",
                columns: table => new
                {
                    ID = table.Column<int>(type: "int", nullable: false)
                        .Annotation("SqlServer:Identity", "1, 1"),
                    StartTime = table.Column<DateTime>(type: "datetime2", nullable: true),
                    EndTime = table.Column<DateTime>(type: "datetime2", nullable: true),
                    EarnedMoney = table.Column<float>(type: "real", nullable: true),
                    PeopleID = table.Column<int>(type: "int", nullable: true),
                    JobID = table.Column<int>(type: "int", nullable: true),
                    WageID = table.Column<int>(type: "int", nullable: true),
                    StatusID = table.Column<int>(type: "int", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK__Shifts__3214EC27EF801669", x => x.ID);
                    table.ForeignKey(
                        name: "FK__Shifts__JobID__160F4887",
                        column: x => x.JobID,
                        principalTable: "Jobs",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__Shifts__PeopleID__151B244E",
                        column: x => x.PeopleID,
                        principalTable: "People",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__Shifts__StatusID__17F790F9",
                        column: x => x.StatusID,
                        principalTable: "States",
                        principalColumn: "ID");
                    table.ForeignKey(
                        name: "FK__Shifts__WageID__17036CC0",
                        column: x => x.WageID,
                        principalTable: "Wages",
                        principalColumn: "ID");
                });

            migrationBuilder.CreateIndex(
                name: "IX_Dashboard_JobID",
                table: "Dashboard",
                column: "JobID");

            migrationBuilder.CreateIndex(
                name: "IX_Dashboard_PeopleID",
                table: "Dashboard",
                column: "PeopleID");

            migrationBuilder.CreateIndex(
                name: "IX_Dashboard_WageID",
                table: "Dashboard",
                column: "WageID");

            migrationBuilder.CreateIndex(
                name: "IX_Jobs_PeopleID",
                table: "Jobs",
                column: "PeopleID");

            migrationBuilder.CreateIndex(
                name: "UQ__Jobs__2CB664DC052710DB",
                table: "Jobs",
                column: "Title",
                unique: true,
                filter: "[Title] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "UQ__People__536C85E4477C00D6",
                table: "People",
                column: "Username",
                unique: true,
                filter: "[Username] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_PeopleJobs_JobID",
                table: "PeopleJobs",
                column: "JobID");

            migrationBuilder.CreateIndex(
                name: "IX_PeopleJobs_PeopleID",
                table: "PeopleJobs",
                column: "PeopleID");

            migrationBuilder.CreateIndex(
                name: "IX_PeopleJobs_RoleID",
                table: "PeopleJobs",
                column: "RoleID");

            migrationBuilder.CreateIndex(
                name: "IX_PeopleJobs_WageID",
                table: "PeopleJobs",
                column: "WageID");

            migrationBuilder.CreateIndex(
                name: "IX_Positions_PeopleID",
                table: "Positions",
                column: "PeopleID");

            migrationBuilder.CreateIndex(
                name: "UQ__Roles__2CB664DC94E0B49A",
                table: "Roles",
                column: "Title",
                unique: true,
                filter: "[Title] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "IX_Shifts_JobID",
                table: "Shifts",
                column: "JobID");

            migrationBuilder.CreateIndex(
                name: "IX_Shifts_PeopleID",
                table: "Shifts",
                column: "PeopleID");

            migrationBuilder.CreateIndex(
                name: "IX_Shifts_StatusID",
                table: "Shifts",
                column: "StatusID");

            migrationBuilder.CreateIndex(
                name: "IX_Shifts_WageID",
                table: "Shifts",
                column: "WageID");

            migrationBuilder.CreateIndex(
                name: "UQ__States__2CB664DCE6DF2B2F",
                table: "States",
                column: "Title",
                unique: true,
                filter: "[Title] IS NOT NULL");

            migrationBuilder.CreateIndex(
                name: "UQ__Wages__737584F6CC418525",
                table: "Wages",
                column: "Name",
                unique: true,
                filter: "[Name] IS NOT NULL");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "Dashboard");

            migrationBuilder.DropTable(
                name: "PeopleJobs");

            migrationBuilder.DropTable(
                name: "Positions");

            migrationBuilder.DropTable(
                name: "Shifts");

            migrationBuilder.DropTable(
                name: "Roles");

            migrationBuilder.DropTable(
                name: "Jobs");

            migrationBuilder.DropTable(
                name: "States");

            migrationBuilder.DropTable(
                name: "Wages");

            migrationBuilder.DropTable(
                name: "People");
        }
    }
}
