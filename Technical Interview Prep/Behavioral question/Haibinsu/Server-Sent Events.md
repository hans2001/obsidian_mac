A web tech that allow user to push updates to client over a single long-lived connection

- Uses a single long-lived HTTP connection
- One-way communication (server to client only)
- Based on the EventSource API in JavaScript
- Text-based protocol using UTF-8
- Native support in most browsers
- Implemented over regular HTTP/HTTPS

## Opening a connection to the server, create a new EventSource object
```
const evtSource = new EventSource("sse-demo.php");
```

## Client script on example.com
```js
const evtSource = new EventSource("//api.example.com/sse-demo.php", {
  withCredentials: true,
});
```

## Listen for message events
```js
evtSource.onmessage = (event) => {
  const newElement = document.createElement("li");
  const eventList = document.getElementById("list");

  newElement.textContent = `message: ${event.data}`;
  eventList.appendChild(newElement);
};
```

# Example
## Server side
```js
const express = require('express');
const app = express();

app.get('/events', (req, res) => {
  // Set SSE headers
  res.setHeader('Content-Type', 'text/event-stream');
  res.setHeader('Cache-Control', 'no-cache');
  res.setHeader('Connection', 'keep-alive');
  
  // Send an initial message
  res.write('data: Connection established\n\n');
  
  // Send a message every 5 seconds
  const intervalId = setInterval(() => {
    const data = JSON.stringify({ time: new Date().toISOString() });
    res.write(`data: ${data}\n\n`);
  }, 5000);
  
  // Clean up when client disconnects
  req.on('close', () => {
    clearInterval(intervalId);
  });
});

app.listen(3000);
```

## Client side
```js
const eventSource = new EventSource('/events');

eventSource.onmessage = (event) => {
  const data = JSON.parse(event.data);
  console.log('New message:', data);
};

eventSource.onerror = (error) => {
  console.error('EventSource error:', error);
  eventSource.close();
};
```

# Comparison 

## SSE vs HTTP events

| Feature         | Server-Sent Events               | Normal HTTP Events                      |
| --------------- | -------------------------------- | --------------------------------------- |
| Connection      | Single persistent connection     | New connection per request              |
| Direction       | One-way (server to client)       | Two-way request-response                |
| Overhead        | Lower for frequent updates       | Higher for frequent updates             |
| Reconnection    | Automatic                        | Manual implementation needed            |
| Format          | Text-based, UTF-8                | Any format (JSON, XML, etc.)            |
| Browser Support | Most modern browsers             | Universal                               |
| Use Case        | Real-time updates, notifications | Traditional web requests                |
| Complexity      | Simple API                       | Requires polling or additional patterns |
|                 |                                  |                                         |
## SSE vs web sockets

| Feature             | WebSockets                           | Server-Sent Events (SSE)                |
| ------------------- | ------------------------------------ | --------------------------------------- |
| **Communication**   | Bidirectional (full-duplex)          | Unidirectional (server to client only)  |
| **Protocol**        | ws:// or wss:// (dedicated protocol) | Standard HTTP/HTTPS                     |
| **Connection**      | Persistent TCP connection            | HTTP connection kept alive              |
| **Data Format**     | Binary or text                       | Text only (UTF-8)                       |
| **Reconnection**    | Manual implementation required       | Automatic reconnection built-in         |
| **Header Support**  | Limited after handshake              | Full HTTP header support                |
| **Complexity**      | More complex implementation          | Simpler implementation                  |
| **Browser Support** | Excellent in modern browsers         | Good, but IE requires polyfill          |
| **Proxy Handling**  | May have issues with some proxies    | Works well with proxies (standard HTTP) |
| **Max Connections** | Higher overhead per connection       | Lower overhead per connection           |
| **Firewalls**       | May be blocked by firewalls          | Rarely blocked (uses standard ports)    |