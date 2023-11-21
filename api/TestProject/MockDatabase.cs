using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage;
using Moq;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SecurityApiTest
{
    public class MockDatabase : DatabaseFacade
    {
        public MockDatabase(DbContext context) : base(context)
        {
        }

        public override Task<IDbContextTransaction> BeginTransactionAsync(CancellationToken cancellationToken) =>
            Task.FromResult(Mock.Of<IDbContextTransaction>());
    }
}
