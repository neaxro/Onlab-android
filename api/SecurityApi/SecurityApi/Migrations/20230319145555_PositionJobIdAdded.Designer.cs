﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using SecurityApi.Context;

#nullable disable

namespace SecurityApi.Migrations
{
    [DbContext(typeof(OnlabContext))]
    [Migration("20230319145555_PositionJobIdAdded")]
    partial class PositionJobIdAdded
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.4")
                .HasAnnotation("Relational:MaxIdentifierLength", 128);

            SqlServerModelBuilderExtensions.UseIdentityColumns(modelBuilder);

            modelBuilder.Entity("SecurityApi.Model.Dashboard", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<DateTime?>("CreationTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("datetime2")
                        .HasDefaultValueSql("(getdate())");

                    b.Property<int?>("JobId")
                        .HasColumnType("int")
                        .HasColumnName("JobID");

                    b.Property<string>("Message")
                        .HasColumnType("text");

                    b.Property<int?>("PeopleId")
                        .HasColumnType("int")
                        .HasColumnName("PeopleID");

                    b.Property<string>("Title")
                        .HasMaxLength(20)
                        .HasColumnType("nvarchar(20)");

                    b.Property<int?>("WageId")
                        .HasColumnType("int")
                        .HasColumnName("WageID");

                    b.HasKey("Id")
                        .HasName("PK__Dashboar__3214EC271DE10989");

                    b.HasIndex("JobId");

                    b.HasIndex("PeopleId");

                    b.HasIndex("WageId");

                    b.ToTable("Dashboard", (string)null);
                });

            modelBuilder.Entity("SecurityApi.Model.Job", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<string>("Description")
                        .HasMaxLength(100)
                        .HasColumnType("nvarchar(100)");

                    b.Property<int?>("PeopleId")
                        .HasColumnType("int")
                        .HasColumnName("PeopleID");

                    b.Property<string>("Pin")
                        .HasMaxLength(6)
                        .IsUnicode(false)
                        .HasColumnType("varchar(6)");

                    b.Property<string>("Title")
                        .HasMaxLength(20)
                        .HasColumnType("nvarchar(20)");

                    b.HasKey("Id")
                        .HasName("PK__Jobs__3214EC27652C1185");

                    b.HasIndex("PeopleId");

                    b.HasIndex(new[] { "Title" }, "UQ__Jobs__2CB664DC8DA9517A")
                        .IsUnique()
                        .HasFilter("[Title] IS NOT NULL");

                    b.ToTable("Jobs");
                });

            modelBuilder.Entity("SecurityApi.Model.PeopleJob", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<int?>("JobId")
                        .HasColumnType("int")
                        .HasColumnName("JobID");

                    b.Property<int?>("PeopleId")
                        .HasColumnType("int")
                        .HasColumnName("PeopleID");

                    b.Property<int?>("RoleId")
                        .HasColumnType("int")
                        .HasColumnName("RoleID");

                    b.Property<int?>("WageId")
                        .HasColumnType("int")
                        .HasColumnName("WageID");

                    b.HasKey("Id")
                        .HasName("PK__PeopleJo__3214EC2754186BAD");

                    b.HasIndex("JobId");

                    b.HasIndex("PeopleId");

                    b.HasIndex("RoleId");

                    b.HasIndex("WageId");

                    b.ToTable("PeopleJobs");
                });

            modelBuilder.Entity("SecurityApi.Model.Person", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<string>("Email")
                        .HasMaxLength(20)
                        .HasColumnType("nvarchar(20)");

                    b.Property<string>("Name")
                        .HasMaxLength(30)
                        .HasColumnType("nvarchar(30)");

                    b.Property<string>("Nickname")
                        .HasMaxLength(30)
                        .HasColumnType("nvarchar(30)");

                    b.Property<string>("Password")
                        .HasMaxLength(30)
                        .HasColumnType("nvarchar(30)");

                    b.Property<byte[]>("ProfilePicture")
                        .HasColumnType("image");

                    b.Property<string>("Username")
                        .HasMaxLength(20)
                        .HasColumnType("nvarchar(20)");

                    b.HasKey("Id")
                        .HasName("PK__People__3214EC272A1B0EEE");

                    b.HasIndex(new[] { "Username" }, "UQ__People__536C85E4FC461098")
                        .IsUnique()
                        .HasFilter("[Username] IS NOT NULL");

                    b.ToTable("People");
                });

            modelBuilder.Entity("SecurityApi.Model.Position", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<int?>("JobId")
                        .HasColumnType("int");

                    b.Property<float?>("Latitude")
                        .HasColumnType("real");

                    b.Property<float?>("Longitude")
                        .HasColumnType("real");

                    b.Property<int?>("PeopleId")
                        .HasColumnType("int")
                        .HasColumnName("PeopleID");

                    b.Property<DateTime?>("Time")
                        .HasColumnType("datetime2");

                    b.HasKey("Id")
                        .HasName("PK__Position__3214EC274F93830D");

                    b.HasIndex("JobId");

                    b.HasIndex("PeopleId");

                    b.ToTable("Positions");
                });

            modelBuilder.Entity("SecurityApi.Model.Role", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<string>("Title")
                        .HasMaxLength(20)
                        .HasColumnType("nvarchar(20)");

                    b.HasKey("Id")
                        .HasName("PK__Roles__3214EC27EEA22C25");

                    b.HasIndex(new[] { "Title" }, "UQ__Roles__2CB664DCA06DE592")
                        .IsUnique()
                        .HasFilter("[Title] IS NOT NULL");

                    b.ToTable("Roles");
                });

            modelBuilder.Entity("SecurityApi.Model.Shift", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<float?>("EarnedMoney")
                        .HasColumnType("real");

                    b.Property<DateTime?>("EndTime")
                        .HasColumnType("datetime2");

                    b.Property<int?>("JobId")
                        .HasColumnType("int")
                        .HasColumnName("JobID");

                    b.Property<int?>("PeopleId")
                        .HasColumnType("int")
                        .HasColumnName("PeopleID");

                    b.Property<DateTime?>("StartTime")
                        .HasColumnType("datetime2");

                    b.Property<int?>("StatusId")
                        .HasColumnType("int")
                        .HasColumnName("StatusID");

                    b.Property<int?>("WageId")
                        .HasColumnType("int")
                        .HasColumnName("WageID");

                    b.HasKey("Id")
                        .HasName("PK__Shifts__3214EC2730CB3811");

                    b.HasIndex("JobId");

                    b.HasIndex("PeopleId");

                    b.HasIndex("StatusId");

                    b.HasIndex("WageId");

                    b.ToTable("Shifts");
                });

            modelBuilder.Entity("SecurityApi.Model.State", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<string>("Description")
                        .HasMaxLength(100)
                        .HasColumnType("nvarchar(100)");

                    b.Property<string>("Title")
                        .HasMaxLength(20)
                        .HasColumnType("nvarchar(20)");

                    b.HasKey("Id")
                        .HasName("PK__States__3214EC27CDE8CA39");

                    b.HasIndex(new[] { "Title" }, "UQ__States__2CB664DCCD74DA51")
                        .IsUnique()
                        .HasFilter("[Title] IS NOT NULL");

                    b.ToTable("States");
                });

            modelBuilder.Entity("SecurityApi.Model.Wage", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

                    b.Property<string>("Name")
                        .HasMaxLength(30)
                        .HasColumnType("nvarchar(30)");

                    b.Property<float?>("Price")
                        .HasColumnType("real");

                    b.HasKey("Id")
                        .HasName("PK__Wages__3214EC27D10E925C");

                    b.HasIndex(new[] { "Name" }, "UQ__Wages__737584F6B54ECFBB")
                        .IsUnique()
                        .HasFilter("[Name] IS NOT NULL");

                    b.ToTable("Wages");
                });

            modelBuilder.Entity("SecurityApi.Model.Dashboard", b =>
                {
                    b.HasOne("SecurityApi.Model.Job", "Job")
                        .WithMany("Dashboards")
                        .HasForeignKey("JobId")
                        .HasConstraintName("FK__Dashboard__JobID__662B2B3B");

                    b.HasOne("SecurityApi.Model.Person", "People")
                        .WithMany("Dashboards")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Dashboard__Peopl__671F4F74");

                    b.HasOne("SecurityApi.Model.Wage", "Wage")
                        .WithMany("Dashboards")
                        .HasForeignKey("WageId")
                        .HasConstraintName("FK__Dashboard__WageI__681373AD");

                    b.Navigation("Job");

                    b.Navigation("People");

                    b.Navigation("Wage");
                });

            modelBuilder.Entity("SecurityApi.Model.Job", b =>
                {
                    b.HasOne("SecurityApi.Model.Person", "People")
                        .WithMany("Jobs")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Jobs__PeopleID__59C55456");

                    b.Navigation("People");
                });

            modelBuilder.Entity("SecurityApi.Model.PeopleJob", b =>
                {
                    b.HasOne("SecurityApi.Model.Job", "Job")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("JobId")
                        .HasConstraintName("FK__PeopleJob__JobID__70A8B9AE");

                    b.HasOne("SecurityApi.Model.Person", "People")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__PeopleJob__Peopl__719CDDE7");

                    b.HasOne("SecurityApi.Model.Role", "Role")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("RoleId")
                        .HasConstraintName("FK__PeopleJob__RoleI__72910220");

                    b.HasOne("SecurityApi.Model.Wage", "Wage")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("WageId")
                        .HasConstraintName("FK__PeopleJob__WageI__73852659");

                    b.Navigation("Job");

                    b.Navigation("People");

                    b.Navigation("Role");

                    b.Navigation("Wage");
                });

            modelBuilder.Entity("SecurityApi.Model.Position", b =>
                {
                    b.HasOne("SecurityApi.Model.Job", "Job")
                        .WithMany("Positions")
                        .HasForeignKey("JobId");

                    b.HasOne("SecurityApi.Model.Person", "People")
                        .WithMany("Positions")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Positions__Peopl__6DCC4D03");

                    b.Navigation("Job");

                    b.Navigation("People");
                });

            modelBuilder.Entity("SecurityApi.Model.Shift", b =>
                {
                    b.HasOne("SecurityApi.Model.Job", "Job")
                        .WithMany("Shifts")
                        .HasForeignKey("JobId")
                        .HasConstraintName("FK__Shifts__JobID__607251E5");

                    b.HasOne("SecurityApi.Model.Person", "People")
                        .WithMany("Shifts")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Shifts__PeopleID__5F7E2DAC");

                    b.HasOne("SecurityApi.Model.State", "Status")
                        .WithMany("Shifts")
                        .HasForeignKey("StatusId")
                        .HasConstraintName("FK__Shifts__StatusID__625A9A57");

                    b.HasOne("SecurityApi.Model.Wage", "Wage")
                        .WithMany("Shifts")
                        .HasForeignKey("WageId")
                        .HasConstraintName("FK__Shifts__WageID__6166761E");

                    b.Navigation("Job");

                    b.Navigation("People");

                    b.Navigation("Status");

                    b.Navigation("Wage");
                });

            modelBuilder.Entity("SecurityApi.Model.Job", b =>
                {
                    b.Navigation("Dashboards");

                    b.Navigation("PeopleJobs");

                    b.Navigation("Positions");

                    b.Navigation("Shifts");
                });

            modelBuilder.Entity("SecurityApi.Model.Person", b =>
                {
                    b.Navigation("Dashboards");

                    b.Navigation("Jobs");

                    b.Navigation("PeopleJobs");

                    b.Navigation("Positions");

                    b.Navigation("Shifts");
                });

            modelBuilder.Entity("SecurityApi.Model.Role", b =>
                {
                    b.Navigation("PeopleJobs");
                });

            modelBuilder.Entity("SecurityApi.Model.State", b =>
                {
                    b.Navigation("Shifts");
                });

            modelBuilder.Entity("SecurityApi.Model.Wage", b =>
                {
                    b.Navigation("Dashboards");

                    b.Navigation("PeopleJobs");

                    b.Navigation("Shifts");
                });
#pragma warning restore 612, 618
        }
    }
}
