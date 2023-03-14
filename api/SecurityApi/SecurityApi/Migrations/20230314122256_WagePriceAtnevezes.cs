using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SecurityApi.Migrations
{
    /// <inheritdoc />
    public partial class WagePriceAtnevezes : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
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

            migrationBuilder.RenameColumn(
                name: "Wage",
                table: "Wages",
                newName: "Price");

            migrationBuilder.RenameIndex(
                name: "UQ__Wages__737584F690805AF8",
                table: "Wages",
                newName: "UQ__Wages__737584F6B54ECFBB");

            migrationBuilder.RenameIndex(
                name: "UQ__States__2CB664DCD995C264",
                table: "States",
                newName: "UQ__States__2CB664DCCD74DA51");

            migrationBuilder.RenameIndex(
                name: "UQ__Roles__2CB664DCF49F7188",
                table: "Roles",
                newName: "UQ__Roles__2CB664DCA06DE592");

            migrationBuilder.RenameIndex(
                name: "UQ__People__536C85E43DDCEEC5",
                table: "People",
                newName: "UQ__People__536C85E4FC461098");

            migrationBuilder.RenameIndex(
                name: "UQ__Jobs__2CB664DCA4025C8F",
                table: "Jobs",
                newName: "UQ__Jobs__2CB664DC8DA9517A");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Wages__3214EC27D10E925C",
                table: "Wages",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__States__3214EC27CDE8CA39",
                table: "States",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Shifts__3214EC2730CB3811",
                table: "Shifts",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Roles__3214EC27EEA22C25",
                table: "Roles",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Position__3214EC274F93830D",
                table: "Positions",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__PeopleJo__3214EC2754186BAD",
                table: "PeopleJobs",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__People__3214EC272A1B0EEE",
                table: "People",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Jobs__3214EC27652C1185",
                table: "Jobs",
                column: "ID");

            migrationBuilder.AddPrimaryKey(
                name: "PK__Dashboar__3214EC271DE10989",
                table: "Dashboard",
                column: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__JobID__662B2B3B",
                table: "Dashboard",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__Peopl__671F4F74",
                table: "Dashboard",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Dashboard__WageI__681373AD",
                table: "Dashboard",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Jobs__PeopleID__59C55456",
                table: "Jobs",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__JobID__70A8B9AE",
                table: "PeopleJobs",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__Peopl__719CDDE7",
                table: "PeopleJobs",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__RoleI__72910220",
                table: "PeopleJobs",
                column: "RoleID",
                principalTable: "Roles",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__PeopleJob__WageI__73852659",
                table: "PeopleJobs",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Positions__Peopl__6DCC4D03",
                table: "Positions",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__JobID__607251E5",
                table: "Shifts",
                column: "JobID",
                principalTable: "Jobs",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__PeopleID__5F7E2DAC",
                table: "Shifts",
                column: "PeopleID",
                principalTable: "People",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__StatusID__625A9A57",
                table: "Shifts",
                column: "StatusID",
                principalTable: "States",
                principalColumn: "ID");

            migrationBuilder.AddForeignKey(
                name: "FK__Shifts__WageID__6166761E",
                table: "Shifts",
                column: "WageID",
                principalTable: "Wages",
                principalColumn: "ID");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__JobID__662B2B3B",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__Peopl__671F4F74",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Dashboard__WageI__681373AD",
                table: "Dashboard");

            migrationBuilder.DropForeignKey(
                name: "FK__Jobs__PeopleID__59C55456",
                table: "Jobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__JobID__70A8B9AE",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__Peopl__719CDDE7",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__RoleI__72910220",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__PeopleJob__WageI__73852659",
                table: "PeopleJobs");

            migrationBuilder.DropForeignKey(
                name: "FK__Positions__Peopl__6DCC4D03",
                table: "Positions");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__JobID__607251E5",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__PeopleID__5F7E2DAC",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__StatusID__625A9A57",
                table: "Shifts");

            migrationBuilder.DropForeignKey(
                name: "FK__Shifts__WageID__6166761E",
                table: "Shifts");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Wages__3214EC27D10E925C",
                table: "Wages");

            migrationBuilder.DropPrimaryKey(
                name: "PK__States__3214EC27CDE8CA39",
                table: "States");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Shifts__3214EC2730CB3811",
                table: "Shifts");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Roles__3214EC27EEA22C25",
                table: "Roles");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Position__3214EC274F93830D",
                table: "Positions");

            migrationBuilder.DropPrimaryKey(
                name: "PK__PeopleJo__3214EC2754186BAD",
                table: "PeopleJobs");

            migrationBuilder.DropPrimaryKey(
                name: "PK__People__3214EC272A1B0EEE",
                table: "People");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Jobs__3214EC27652C1185",
                table: "Jobs");

            migrationBuilder.DropPrimaryKey(
                name: "PK__Dashboar__3214EC271DE10989",
                table: "Dashboard");

            migrationBuilder.RenameColumn(
                name: "Price",
                table: "Wages",
                newName: "Wage");

            migrationBuilder.RenameIndex(
                name: "UQ__Wages__737584F6B54ECFBB",
                table: "Wages",
                newName: "UQ__Wages__737584F690805AF8");

            migrationBuilder.RenameIndex(
                name: "UQ__States__2CB664DCCD74DA51",
                table: "States",
                newName: "UQ__States__2CB664DCD995C264");

            migrationBuilder.RenameIndex(
                name: "UQ__Roles__2CB664DCA06DE592",
                table: "Roles",
                newName: "UQ__Roles__2CB664DCF49F7188");

            migrationBuilder.RenameIndex(
                name: "UQ__People__536C85E4FC461098",
                table: "People",
                newName: "UQ__People__536C85E43DDCEEC5");

            migrationBuilder.RenameIndex(
                name: "UQ__Jobs__2CB664DC8DA9517A",
                table: "Jobs",
                newName: "UQ__Jobs__2CB664DCA4025C8F");

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
    }
}
