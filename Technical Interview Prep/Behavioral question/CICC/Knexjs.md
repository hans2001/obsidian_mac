SQL query builder for Node.js
chainable API for constructing SQL queries
support migrations / transactions and connection pooling

Basic
```javascript
const knex = require('knex')({
  client: 'pg', // or 'mysql', 'sqlite3', etc.
  connection: {
    host: '127.0.0.1',
    user: 'your_database_user',
    password: 'your_database_password',
    database: 'myapp_test'
  }
});

knex.select('*').from('users')
  .then(rows => {
    console.log(rows);
  })
  .catch(err => {
    console.error(err);
  })
  .finally(() => {
    knex.destroy(); // close the connection pool
  });
```

Data insertion
```javascript
knex('users')
  .insert({ name: 'Alice', age: 30 })
  .then(() => {
    console.log('Data inserted');
  })
  .catch(err => {
    console.error(err);
  });
```

Update records
```javascript
knex('users')
  .where({ id: 1 })
  .update({ age: 31 })
  .then(() => {
    console.log('Data updated');
  })
  .catch(err => {
    console.error(err);
  });
```

Transactions
allow me to execute a series of database operations atomically - either all operations succeed or none do.
either use a callback or manage manually? 

Callback: 
```javascript
knex.transaction(trx => {
  return trx('accounts')
    .where('id', '=', 1)
    .update({ balance: 500 })
    .then(() => {
      return trx('accounts')
        .where('id', '=', 2)
        .update({ balance: 1500 });
    });
})
.then(() => {
  console.log('Transaction complete.');
})
.catch(err => {
  console.error('Transaction failed:', err);
});
```
if any queries failed, Knex will automatically rollback the transaction

Manual ( use promise ):
```javascript
async function manualTransaction() {
  const trx = await knex.transaction();
  try {
    await trx('accounts').where({ id: 1 }).update({ balance: 500 });
    await trx('accounts').where({ id: 2 }).update({ balance: 1500 });
    await trx.commit();
    console.log('Transaction committed successfully.');
  } catch (err) {
    await trx.rollback();
    console.error('Transaction rolled back due to error:', err);
  }
}

manualTransaction();
```

Rollback
```javascript
const test = require('tape');

test('example transaction rollback in test', async t => {
  const trx = await knex.transaction();
  try {
    // Perform test operations within the transaction:
    await trx('users').insert({ name: 'Test User', age: 25 });
    const users = await trx('users').select('*');
    t.ok(users.length > 0, 'should insert user');

    // Instead of committing, rollback to undo changes:
    await trx.rollback();
    t.pass('Transaction rolled back successfully');
  } catch (err) {
    await trx.rollback();
    t.fail('Transaction failed: ' + err.message);
  } finally {
    t.end();
  }
});

```

Migrations
manage changes to database schema over time. define versioned changes( like creating tables, adding columns etc), can be run, rolled back or re-run as needed! 

example:
```javascript
exports.up = function(knex) {
  return knex.schema.createTable('users', function(table) {
    table.increments('id').primary();
    table.string('name').notNullable();
    table.integer('age');
    table.timestamps(true, true);
  });
};

exports.down = function(knex) {
  return knex.schema.dropTable('users');
};
```

Run migration using Knex cli :
```bash
npx knex migrate:latest
```

rollback:
```bash
npx knex migrate:rollback
```

testing:
before test:
```javascript
// test/setup.js
const knex = require('../db/knex'); // your Knex configuration

module.exports = async function setup() {
  try {
    await knex.migrate.latest();
    console.log('Migrations applied');
  } catch (err) {
    console.error('Migration error:', err);
  }
};
```

after test -> rollback (teardown file)
```javascript
// test/teardown.js
const knex = require('../db/knex');

module.exports = async function teardown() {
  try {
    await knex.migrate.rollback();
    console.log('Migrations rolled back');
  } catch (err) {
    console.error('Teardown error:', err);
  } finally {
    await knex.destroy();
  }
};
```

up & down method
```javascript
// Migration file: 20250324120000_create_users_table.js
exports.up = function(knex) {
  return knex.schema.createTable('users', function(table) {
    table.increments('id').primary();
    table.string('name').notNullable();
    table.integer('age');
    table.timestamps(true, true);
  });
};

exports.down = function(knex) {
  return knex.schema.dropTable('users');
};

```

The **up** method creates a new table called `users` with an auto-incrementing `id`, a non-nullable `name` field, an `age` field, and timestamp columns.

The **down** method drops the `users` table, effectively reverting the changes made by the up method.

Goal:
maintain clean state of the database
maintain atomicity of database operations 
abstract away sql syntax different between different databases