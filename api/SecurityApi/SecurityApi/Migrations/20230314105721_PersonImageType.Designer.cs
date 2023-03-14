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
    [Migration("20230314105721_PersonImageType")]
    partial class PersonImageType
    {
        /// <inheritdoc />
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "7.0.3")
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
                        .HasName("PK__Dashboar__3214EC2724CE1DDD");

                    b.HasIndex("JobId");

                    b.HasIndex("PeopleId");

                    b.HasIndex("WageId");

                    b.ToTable("Dashboard", (string)null);
                });

            modelBuilder.Entity("SecurityApi.Model.DbPerson", b =>
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
                        .HasName("PK__People__3214EC27A6BA1D9E");

                    b.HasIndex(new[] { "Username" }, "UQ__People__536C85E43DDCEEC5")
                        .IsUnique()
                        .HasFilter("[Username] IS NOT NULL");

                    b.ToTable("People");
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
                        .HasName("PK__Jobs__3214EC27433AE975");

                    b.HasIndex("PeopleId");

                    b.HasIndex(new[] { "Title" }, "UQ__Jobs__2CB664DCA4025C8F")
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
                        .HasName("PK__PeopleJo__3214EC270AC63FE6");

                    b.HasIndex("JobId");

                    b.HasIndex("PeopleId");

                    b.HasIndex("RoleId");

                    b.HasIndex("WageId");

                    b.ToTable("PeopleJobs");
                });

            modelBuilder.Entity("SecurityApi.Model.Position", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("int")
                        .HasColumnName("ID");

                    SqlServerPropertyBuilderExtensions.UseIdentityColumn(b.Property<int>("Id"));

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
                        .HasName("PK__Position__3214EC27BE98AA82");

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
                        .HasName("PK__Roles__3214EC27328BC743");

                    b.HasIndex(new[] { "Title" }, "UQ__Roles__2CB664DCF49F7188")
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
                        .HasName("PK__Shifts__3214EC2799175283");

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
                        .HasName("PK__States__3214EC27BBD22DBD");

                    b.HasIndex(new[] { "Title" }, "UQ__States__2CB664DCD995C264")
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

                    b.Property<float?>("Wage1")
                        .HasColumnType("real")
                        .HasColumnName("Wage");

                    b.HasKey("Id")
                        .HasName("PK__Wages__3214EC2743945115");

                    b.HasIndex(new[] { "Name" }, "UQ__Wages__737584F690805AF8")
                        .IsUnique()
                        .HasFilter("[Name] IS NOT NULL");

                    b.ToTable("Wages");
                });

            modelBuilder.Entity("SecurityApi.Model.Dashboard", b =>
                {
                    b.HasOne("SecurityApi.Model.Job", "Job")
                        .WithMany("Dashboards")
                        .HasForeignKey("JobId")
                        .HasConstraintName("FK__Dashboard__JobID__41EDCAC5");

                    b.HasOne("SecurityApi.Model.DbPerson", "People")
                        .WithMany("Dashboards")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Dashboard__Peopl__42E1EEFE");

                    b.HasOne("SecurityApi.Model.Wage", "Wage")
                        .WithMany("Dashboards")
                        .HasForeignKey("WageId")
                        .HasConstraintName("FK__Dashboard__WageI__43D61337");

                    b.Navigation("Job");

                    b.Navigation("People");

                    b.Navigation("Wage");
                });

            modelBuilder.Entity("SecurityApi.Model.Job", b =>
                {
                    b.HasOne("SecurityApi.Model.DbPerson", "People")
                        .WithMany("Jobs")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Jobs__PeopleID__3587F3E0");

                    b.Navigation("People");
                });

            modelBuilder.Entity("SecurityApi.Model.PeopleJob", b =>
                {
                    b.HasOne("SecurityApi.Model.Job", "Job")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("JobId")
                        .HasConstraintName("FK__PeopleJob__JobID__4C6B5938");

                    b.HasOne("SecurityApi.Model.DbPerson", "People")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__PeopleJob__Peopl__4D5F7D71");

                    b.HasOne("SecurityApi.Model.Role", "Role")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("RoleId")
                        .HasConstraintName("FK__PeopleJob__RoleI__4E53A1AA");

                    b.HasOne("SecurityApi.Model.Wage", "Wage")
                        .WithMany("PeopleJobs")
                        .HasForeignKey("WageId")
                        .HasConstraintName("FK__PeopleJob__WageI__4F47C5E3");

                    b.Navigation("Job");

                    b.Navigation("People");

                    b.Navigation("Role");

                    b.Navigation("Wage");
                });

            modelBuilder.Entity("SecurityApi.Model.Position", b =>
                {
                    b.HasOne("SecurityApi.Model.DbPerson", "People")
                        .WithMany("Positions")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Positions__Peopl__498EEC8D");

                    b.Navigation("People");
                });

            modelBuilder.Entity("SecurityApi.Model.Shift", b =>
                {
                    b.HasOne("SecurityApi.Model.Job", "Job")
                        .WithMany("Shifts")
                        .HasForeignKey("JobId")
                        .HasConstraintName("FK__Shifts__JobID__3C34F16F");

                    b.HasOne("SecurityApi.Model.DbPerson", "People")
                        .WithMany("Shifts")
                        .HasForeignKey("PeopleId")
                        .HasConstraintName("FK__Shifts__PeopleID__3B40CD36");

                    b.HasOne("SecurityApi.Model.State", "Status")
                        .WithMany("Shifts")
                        .HasForeignKey("StatusId")
                        .HasConstraintName("FK__Shifts__StatusID__3E1D39E1");

                    b.HasOne("SecurityApi.Model.Wage", "Wage")
                        .WithMany("Shifts")
                        .HasForeignKey("WageId")
                        .HasConstraintName("FK__Shifts__WageID__3D2915A8");

                    b.Navigation("Job");

                    b.Navigation("People");

                    b.Navigation("Status");

                    b.Navigation("Wage");
                });

            modelBuilder.Entity("SecurityApi.Model.DbPerson", b =>
                {
                    b.Navigation("Dashboards");

                    b.Navigation("Jobs");

                    b.Navigation("PeopleJobs");

                    b.Navigation("Positions");

                    b.Navigation("Shifts");
                });

            modelBuilder.Entity("SecurityApi.Model.Job", b =>
                {
                    b.Navigation("Dashboards");

                    b.Navigation("PeopleJobs");

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
