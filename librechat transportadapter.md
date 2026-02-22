Purpose

- TransportAdapter wraps HTTP/stream transport to model or auxiliary APIs.

Responsibilities

- Execute POST or streaming calls with retries/timeouts/backoff.

- Normalize provider responses.

Integration

- Used by provider subclasses within sendCompletion() (the test providers currently simulate behavior; a real provider would call transport).