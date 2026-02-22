specify number of assertions with t.plan( )
We use assertion methods like `t.equal()` to compare actual and expected values.
t.end() to makr completion 

self-contained test function -> reduce side effects

unit test: 
test individual function or modules in separation
```javascript
const test = require('tape');

function add(a, b) {
  return a + b;
}

test('add function should correctly add two numbers', t => {
  t.equal(add(1, 1), 2, '1 + 1 should equal 2');
  t.end();
});
```

integration test: 
test interaction between several modules
```javascript
const test = require('tape');
const request = require('request'); // Assume you're using this for HTTP requests

// Simulate an integration test of an HTTP server endpoint
test('GET /api/data returns expected results', t => {
  request.get('http://localhost:3000/api/data', (err, res, body) => {
    t.error(err, 'No error should occur');
    t.equal(res.statusCode, 200, 'Response status should be 200');
    let data;
    try {
      data = JSON.parse(body);
    } catch (e) {
      t.fail('Response body is not valid JSON');
    }
    t.ok(Array.isArray(data), 'Data should be an array');
    t.end();
  });
});
```
assertion checks for errors, HTTP status and format of returned data

```javascript
const test = require('tape');
const request = require('request'); // Ensure you have 'request' installed via npm

test('POST /api/items creates a new item and GET /api/items returns it', t => {
  const newItem = { name: 'Test Item', value: 42 };

  // First, perform a POST request to create a new item
  request.post({
    url: 'http://localhost:3000/api/items',
    json: newItem
  }, (postErr, postRes, postBody) => {
    t.error(postErr, 'No error should occur during POST');
    t.equal(postRes.statusCode, 201, 'POST response status should be 201 for creation');

    // Then, perform a GET request to retrieve all items
    request.get({
      url: 'http://localhost:3000/api/items',
      json: true
    }, (getErr, getRes, getBody) => {
      t.error(getErr, 'No error should occur during GET');
      t.equal(getRes.statusCode, 200, 'GET response status should be 200');

      // Check if the newly created item exists in the returned list
      const foundItem = getBody.find(item => item.name === 'Test Item' && item.value === 42);
      t.ok(foundItem, 'Newly created item should be present in the list');

      t.end();
    });
  });
});
```
verifies that the newly created item exists in the list returned by the GET request
It demonstrates how to chain async operations within a tape test -> end to end integration test covers multiple components

key points:
Test Anything Protocol (TAP) format
Async support (network request, file I/O can be tested)
No Built-in Setup/Teardown Hooks