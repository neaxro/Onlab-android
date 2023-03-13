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
        => optionsBuilder.UseSqlServer("Server=.\\SQLExpress;Database=Onlab;Trusted_Connection=True;TrustServerCertificate=True;Encrypt=False");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Dashboard>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Dashboar__3214EC27EB0A7CCD");

            entity.ToTable("Dashboard");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.CreationTime).HasDefaultValueSql("(getdate())");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.Message).HasColumnType("text");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.Title).HasMaxLength(20);
            entity.Property(e => e.WageId).HasColumnName("WageID");

            entity.HasOne(d => d.Job).WithMany(p => p.Dashboards)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__Dashboard__JobID__1BC821DD");

            entity.HasOne(d => d.People).WithMany(p => p.Dashboards)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Dashboard__Peopl__1CBC4616");

            entity.HasOne(d => d.Wage).WithMany(p => p.Dashboards)
                .HasForeignKey(d => d.WageId)
                .HasConstraintName("FK__Dashboard__WageI__1DB06A4F");
        });

        modelBuilder.Entity<Job>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Jobs__3214EC27DAAB7DC5");

            entity.HasIndex(e => e.Title, "UQ__Jobs__2CB664DC052710DB").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Description).HasMaxLength(100);
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.Pin)
                .HasMaxLength(6)
                .IsUnicode(false);
            entity.Property(e => e.Title).HasMaxLength(20);

            entity.HasOne(d => d.People).WithMany(p => p.Jobs)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Jobs__PeopleID__0F624AF8");
        });

        modelBuilder.Entity<PeopleJob>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__PeopleJo__3214EC2702AC13E0");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.RoleId).HasColumnName("RoleID");
            entity.Property(e => e.WageId).HasColumnName("WageID");

            entity.HasOne(d => d.Job).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__PeopleJob__JobID__2645B050");

            entity.HasOne(d => d.People).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__PeopleJob__Peopl__2739D489");

            entity.HasOne(d => d.Role).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.RoleId)
                .HasConstraintName("FK__PeopleJob__RoleI__282DF8C2");

            entity.HasOne(d => d.Wage).WithMany(p => p.PeopleJobs)
                .HasForeignKey(d => d.WageId)
                .HasConstraintName("FK__PeopleJob__WageI__29221CFB");
        });

        modelBuilder.Entity<Person>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__People__3214EC272E84FF20");

            entity.HasIndex(e => e.Username, "UQ__People__536C85E4477C00D6").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Email).HasMaxLength(20);
            entity.Property(e => e.Name).HasMaxLength(30);
            entity.Property(e => e.Nickname).HasMaxLength(30);
            entity.Property(e => e.Password).HasMaxLength(30);
            entity.Property(e => e.ProfilePicture).HasColumnType("text");
            entity.Property(e => e.Username).HasMaxLength(20);
        });

        modelBuilder.Entity<Position>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Position__3214EC27A8B08031");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");

            entity.HasOne(d => d.People).WithMany(p => p.Positions)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Positions__Peopl__236943A5");
        });

        modelBuilder.Entity<Role>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Roles__3214EC2775EA9E73");

            entity.HasIndex(e => e.Title, "UQ__Roles__2CB664DC94E0B49A").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Title).HasMaxLength(20);
        });

        modelBuilder.Entity<Shift>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Shifts__3214EC27EF801669");

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.JobId).HasColumnName("JobID");
            entity.Property(e => e.PeopleId).HasColumnName("PeopleID");
            entity.Property(e => e.StatusId).HasColumnName("StatusID");
            entity.Property(e => e.WageId).HasColumnName("WageID");

            entity.HasOne(d => d.Job).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.JobId)
                .HasConstraintName("FK__Shifts__JobID__160F4887");

            entity.HasOne(d => d.People).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.PeopleId)
                .HasConstraintName("FK__Shifts__PeopleID__151B244E");

            entity.HasOne(d => d.Status).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.StatusId)
                .HasConstraintName("FK__Shifts__StatusID__17F790F9");

            entity.HasOne(d => d.Wage).WithMany(p => p.Shifts)
                .HasForeignKey(d => d.WageId)
                .HasConstraintName("FK__Shifts__WageID__17036CC0");
        });

        modelBuilder.Entity<State>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__States__3214EC27FCF937CA");

            entity.HasIndex(e => e.Title, "UQ__States__2CB664DCE6DF2B2F").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Description).HasMaxLength(100);
            entity.Property(e => e.Title).HasMaxLength(20);
        });

        modelBuilder.Entity<Wage>(entity =>
        {
            entity.HasKey(e => e.Id).HasName("PK__Wages__3214EC27E1C7D797");

            entity.HasIndex(e => e.Name, "UQ__Wages__737584F6CC418525").IsUnique();

            entity.Property(e => e.Id).HasColumnName("ID");
            entity.Property(e => e.Name).HasMaxLength(30);
            entity.Property(e => e.Wage1).HasColumnName("Wage");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
