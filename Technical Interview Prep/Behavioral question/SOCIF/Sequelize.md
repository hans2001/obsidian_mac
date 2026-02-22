Setup connection
```javascript
const { Sequelize } = require('sequelize');

// Create a connection instance
const sequelize = new Sequelize('database_name', 'username', 'password', {
  host: 'localhost',
  dialect: 'postgres', // change to 'mysql', 'sqlite', etc.
});

// Test the connection
sequelize.authenticate()
  .then(() => console.log('Connection established successfully.'))
  .catch(err => console.error('Unable to connect to the database:', err));
```

Define models
create model by extending the Model class or use sequelize.define
```javascript
const { DataTypes } = require('sequelize');

const User = sequelize.define('User', {
  // Define attributes
  firstName: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  lastName: {
    type: DataTypes.STRING,
    allowNull: false,
  },
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
  }
}, {
  // Other model options go here
});
```

Create 
```javascript
User.create({
  firstName: 'John',
  lastName: 'Doe',
  email: 'john.doe@example.com'
}).then(user => console.log(user.toJSON()));

```

Read
```javascript
User.findOne({ where: { email: 'john.doe@example.com' } })
  .then(user => console.log(user));
User.findAll()
  .then(users => console.log(users));
```

Update 
```javascript
User.update({ lastName: 'Smith' }, {
  where: { email: 'john.doe@example.com' }
}).then(() => console.log('User updated!'));

```

Delete
```javascript
User.destroy({
  where: { email: 'john.doe@example.com' }
}).then(() => console.log('User deleted!'));

```

Associations
- **One-to-One:**  
Connects one record from a model to one record in another. For instance, a `User` might have one `Profile`.
- **One-to-Many:**  
Associates one record from a model with multiple records in another. An example would be a single `User` having many `Posts`.
- **Many-to-Many:**  
Links records from two models where each can have multiple associations with the other, such as `Students` and `Courses`where a student can enroll in many courses and a course can have many students. This is usually implemented using a junction (or join) table.

Define one-to-many relationship
```javascript
// Define the Task model
const Task = sequelize.define('Task', {
  title: DataTypes.STRING,
  description: DataTypes.TEXT,
});

// Create an association: one User has many Tasks
User.hasMany(Task);
Task.belongsTo(User);

// After defining associations, sync models
sequelize.sync();
```
This allows you to use methods such as `user.getTasks()` or `task.getUser()` to retrieve associated data.

Advanced associations and eager loading
```javascript
const User = sequelize.define('User', {
  name: DataTypes.STRING,
  email: DataTypes.STRING
});

const Post = sequelize.define('Post', {
  title: DataTypes.STRING,
  content: DataTypes.TEXT
});

// One User has many Posts, and a Post belongs to one User
User.hasMany(Post, { foreignKey: 'userId' });
Post.belongsTo(User, { foreignKey: 'userId' });
```

fetch a user along with all their posts
```javascript
User.findOne({
  where: { id: 1 },
  include: Post  // Automatically includes all posts for this user
}).then(user => {
  console.log(user);
});
```

Validation and error handling
Sequelize supports built-in and custom validations. You can define validations directly in your model attributes, and Sequelize will enforce them before persisting data:
```javascript
const User = sequelize.define('User', {
  email: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true,
    validate: {
      isEmail: true,
      customValidator(value) {
        if (!value.endsWith('@example.com')) {
          throw new Error('Email must be from example.com domain');
        }
      }
    }
  }
});
```

Lifecycle Hooks
Hooks (or lifecycle events) allow you to execute custom logic before or after certain actions (like create, update, delete). This is useful for validation, logging, or even modifying data:
```javascript 
User.beforeCreate((user, options) => {
  // Hash a password, set default values, etc.
  console.log('About to create a new user:', user.name);
});

User.afterUpdate((user, options) => {
  console.log('User updated:', user.name);
});
```

Custom Getters, Setters, and Virtual Fields
You can customize how fields are retrieved or set. Virtual fields allow you to define computed properties that don’t persist in the database, Instead, they are defined by getter (and optionally setter) methods that calculate their value dynamically based on other fields. They offer a way to encapsulate business logic directly in your model without altering your database schema.
```javascript
const User = sequelize.define('User', {
  firstName: DataTypes.STRING,
  lastName: DataTypes.STRING,
}, {
  getterMethods: {
    fullName() {
      return `${this.firstName} ${this.lastName}`;
    }
  },
  setterMethods: {
    fullName(value) {
      const names = value.split(' ');
      this.setDataValue('firstName', names.slice(0, -1).join(' '));
      this.setDataValue('lastName', names.slice(-1).join(' '));
    }
  }
});

// Using the virtual field
User.create({ firstName: 'Alice', lastName: 'Smith' }).then(user => {
  console.log(user.fullName); // "Alice Smith"
});
```
This feature can be particularly useful for data transformation and ensuring a cleaner API.

Scopes and Query Customization
Scopes let you define common query options that can be reused across your application. For instance, if you often need to filter for active users, you can set up a scope
```javascript
const User = sequelize.define('User', {
  name: DataTypes.STRING,
  active: DataTypes.BOOLEAN,
}, {
  defaultScope: {
    where: { active: true }
  },
  scopes: {
    inactive: {
      where: { active: false }
    },
    withName(name) {
      return {
        where: { name }
      };
    }
  }
});

// Using the scope:
User.scope('inactive').findAll();
User.scope({ method: ['withName', 'Alice'] }).findAll();
```

Eager loading
fetch associated data along with primary query
instead of making multiple calls( N+1 query problem ), eager loading allow inclusion of related models

Imagine you have two models: `User` and `Post`, where a user has many posts. Without eager loading, fetching a user and then iterating over each user to fetch posts would result in multiple queries. With eager loading, you can do it in one query
```javascript
User.findOne({
  where: { id: 1 },
  include: { model: Post }  // Eagerly loads all posts for the user
})
  .then(user => {
    console.log(user);
    // Access posts with user.Posts if the association was defined as hasMany
  })
  .catch(error => console.error(error));
```

for nested eager loading, each post has many comments, we can load
```javascript
User.findOne({
  where: { id: 1 },
  include: [{
    model: Post,
    include: [Comment]  // Eagerly loads comments for each post
  }]
})
  .then(user => {
    console.log(user);
  })
  .catch(error => console.error(error));
```