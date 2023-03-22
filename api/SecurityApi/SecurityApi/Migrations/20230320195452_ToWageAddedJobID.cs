using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace SecurityApi.Migrations
{
    /// <inheritdoc />
    public partial class ToWageAddedJobID : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "JobId",
                table: "Wages",
                type: "int",
                nullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Wages_JobId",
                table: "Wages",
                column: "JobId");

            migrationBuilder.AddForeignKey(
                name: "FK_Wages_Jobs_JobId",
                table: "Wages",
                column: "JobId",
                principalTable: "Jobs",
                principalColumn: "ID");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Wages_Jobs_JobId",
                table: "Wages");

            migrationBuilder.DropIndex(
                name: "IX_Wages_JobId",
                table: "Wages");

            migrationBuilder.DropColumn(
                name: "JobId",
                table: "Wages");
        }
    }
}
