using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using SecurityApi.Model;

namespace SecurityApi.Context;

public partial class OnlabContext : DbContext
{
    public OnlabContext()
    {
    }

    public OnlabContext(DbContextOptions<OnlabContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Dashboard> Dashboards { get; set; }

    public virtual DbSet<Job> Jobs { get; set; }

    public virtual DbSet<PeopleJob> PeopleJobs { get; set; }

    public virtual DbSet<Person> People { get; set; }

    public virtual DbSet<Position> Positions { get; set; }

    public virtual DbSet<Role> Roles { get; set; }

    public virtual DbSet<Shift> Shifts { get; set; }

    public virtual DbSet<State> States { get; set; }

    public virtual DbSet<Wage> Wages { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseSqlServer("Server=.\\SQLExpress;Database=Onlab;User Id=sa;Password=Asdasd11;Trusted_Connection=True;TrustServerCertificate=True;Encrypt=False");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Dashboard>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Dashboar__3214EC274868E79F");

            entity.ToTable("Dashboard");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.CreationTime).HasDefaultValueSql("(getdate())");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.Message).HasColumnType("text");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.Title).HasMaxLength(40);
            entity.Property(e => e.WageId).HasColumnName("WageID");

            entity.HasOne(d => d.Job).WithMany(p => p.Dashboards)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__Dashboard__JobID__029D4CB7");

            entity.HasOne(d => d.People).WithMany(p => p.Dashboards)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Dashboard__Peopl__039170F0");

            entity.HasOne(d => d.Wage).WithMany(p => p.Dashboards)
                .HasForeignKey(d => d.WageId)
                .HasConstraintName("FK__Dashboard__WageI__04859529");
        });

        modelBuilder.Entity<Job>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Jobs__3214EC27D8B4F989");

            entity.HasIndex(e => e.Pin, "IX_Jobs_Pin");

            entity.HasIndex(e => e.Title, "UQ__Jobs__2CB664DC4C2E43FA").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Description).HasMaxLength(150);
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.Pin)
                .HasMaxLength(6)
                .IsUnicode(false);
            entity.Property(e => e.Title).HasMaxLength(30);

            entity.HasOne(d => d.People).WithMany(p => p.Jobs)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Jobs__PeopleID__7266E4EE");
        });

        modelBuilder.Entity<PeopleJob>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__PeopleJo__3214EC27233B9CE0");

            entity.HasIndex(e => e.JobId, "IX_PeopleJobs_JobID");

            entity.HasIndex(e => e.PeopleId, "IX_PeopleJobs_PersonID");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.RoleId).HasColumnName("RoleID");
            entity.Property(e => e.WageId).HasColumnName("WageID");

            entity.HasOne(d => d.Job).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__PeopleJob__JobID__0E0EFF63");

            entity.HasOne(d => d.People).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__PeopleJob__Peopl__0F03239C");

            entity.HasOne(d => d.Role).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.RoleId)
                .HasConstraintName("FK__PeopleJob__RoleI__0FF747D5");

            entity.HasOne(d => d.Wage).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.WageId)
                .HasConstraintName("FK__PeopleJob__WageI__10EB6C0E");
        });

        modelBuilder.Entity<Person>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__People__3214EC275CF40066");

            entity.HasIndex(e => e.Name, "IX_People_Name");

            entity.HasIndex(e => e.Nickname, "IX_People_Nickname");

            entity.HasIndex(e => e.Username, "UQ__People__536C85E42AD5FAA5").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Email).HasMaxLength(50);
            entity.Property(e => e.Name).HasMaxLength(30);
            entity.Property(e => e.Nickname).HasMaxLength(30);
            entity.Property(e => e.Password)
                .HasMaxLength(64)
                .IsUnicode(false)
                .IsFixedLength();
            entity.Property(e => e.ProfilePicture).HasColumnType("image");
            entity.Property(e => e.Username).HasMaxLength(30);
        });

        modelBuilder.Entity<Position>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Position__3214EC272761C302");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");

            entity.HasOne(d => d.Job).WithMany(p => p.Positions)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__Positions__JobID__0B3292B8");

            entity.HasOne(d => d.People).WithMany(p => p.Positions)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Positions__Peopl__0A3E6E7F");
        });

        modelBuilder.Entity<Role>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Roles__3214EC27E5B04F26");

            entity.HasIndex(e => e.Title, "UQ__Roles__2CB664DCD9A95C5E").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Title).HasMaxLength(30);
        });

        modelBuilder.Entity<Shift>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Shifts__3214EC273D628271");

            entity.HasIndex(e => e.JobId, "IX_Shifts_JobID");

            entity.HasIndex(e => e.PeopleId, "IX_Shifts_PeopleID");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.StartTime).HasDefaultValueSql("(getdate())");
            entity.Property(e => e.StatusId).HasColumnName("StatusID");
            entity.Property(e => e.WageId).HasColumnName("WageID");

            entity.HasOne(d => d.Job).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__Shifts__JobID__7CE47361");

            entity.HasOne(d => d.People).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Shifts__PeopleID__7BF04F28");

            entity.HasOne(d => d.Status).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.StatusId)
                .HasConstraintName("FK__Shifts__StatusID__7ECCBBD3");

            entity.HasOne(d => d.Wage).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.WageId)
                .HasConstraintName("FK__Shifts__WageID__7DD8979A");
        });

        modelBuilder.Entity<State>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__States__3214EC273A50D84C");

            entity.HasIndex(e => e.Title, "UQ__States__2CB664DCFC5E1475").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Description).HasMaxLength(100);
            entity.Property(e => e.Title).HasMaxLength(40);
        });

        modelBuilder.Entity<Wage>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Wages__3214EC2754D38EED");

            entity.HasIndex(e => e.JobId, "IX_Wages_JobID");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.Name).HasMaxLength(30);

            entity.HasOne(d => d.Job).WithMany(p => p.Wages)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__Wages__JobID__75435199");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
