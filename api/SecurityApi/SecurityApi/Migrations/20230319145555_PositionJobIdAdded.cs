using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SecurityApi.Migrations
{
    /// <inheritdoc />
    public partial class PositionJobIdAdded : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "JobId",
                table: "Positions",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Positions_JobId",
                table: "Positions",
                column: "JobId");

            migrationBuilder.AddForeignKey(
                name: "FK_Positions_Jobs_JobId",
                table: "Positions",
                column: "JobId",
                principalTable: "Jobs",
                principalColumn: "ID");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Positions_Jobs_JobId",
                table: "Positions");

            migrationBuilder.DropIndex(
                name: "IX_Positions_JobId",
                table: "Positions");

            migrationBuilder.DropColumn(
                name: "JobId",
                table: "Positions");
        }
    }
}
