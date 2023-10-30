using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SecurityApiTest
{
    public interface IDbContextTransactionProxy : IDisposable
    {
        void Commit();

        void Rollback();
    }

    /// <summary>
    /// This is proxy. We want accessing control of DbContextTransaction class.
    /// Because we can't write unit test for BeginTransaction.
    /// DbContextTransaction does not have public constructors.
    /// </summary>
    public class DbContextTransactionProxy : IDbContextTransactionProxy
    {
        /// <summary>
        /// Real Class which we want to control.
        /// We can't mock it's because it does not have public constructors.
        /// </summary>
        private readonly IDbContextTransaction _transaction;

        public DbContextTransactionProxy(DbContext context)
        {
            _transaction = context.Database.BeginTransaction();
        }

        public void Commit()
        {
            _transaction.Commit();
        }

        public void Rollback()
        {
            _transaction.Rollback();
        }

        public void Dispose()
        {
            _transaction.Dispose();
        }
    }
}
