### Tokenized Request Flow — Project-Verified Guide

- Goal: Process LLM requests safely by enforcing balance, fitting model token limits, and recording actual token usage.
## 1) Balance Checking (pre-request gate)

- Checks user credits before any API call; rejects early if insufficient.
- Auto-refill may occur based on balance record settings.
## 2) Token Counting (estimate → actual)

- Estimates before calls; records actual usage after responses.
- Text tokenization uses per-endpoint encodings; message-level counting includes chat overhead.
- Reasoning tokens (when present) are tracked separately.

## 3) Model Token Limits (context/prompt/response)

- Context limit and output cap are resolved per model and endpoint.
- Prompt budget = context limit − max output tokens (with additional safeguards for endpoint quirks).

## 4) API Call (completion/streaming)

- BaseClient.sendMessage builds the payload, enforces budget, then delegates to endpoint client for the request.

- Actual usage metrics (prompt, completion, and sometimes reasoning/cache details) are captured from the API response.

## 5) Usage Recording (post-request)

- Writes negative transactions for prompt/completion tokens; zero-token cases are safely handled.

- Structured tokens (cache read/write/input) are supported for endpoints that return them.

## 6) Token Transactions (database)

- Persists detailed transaction metadata (user, model, conversation, context).
- Auto-refill transactions handled in balance layer.

## 7) Context Management (long conversations)

- Sliding window keeps the most recent content verbatim within prompt budget.

- Summarization condenses earlier history; can reuse previous summary if available.

- If still over limit, older/deep history is dropped or the request is rejected when instructions alone exceed limits.

## Rate Limiting Model

- Primarily quota-based by total token consumption (balance-backed).

- Frequency limits, if any, are secondary to quota enforcement.

## Practical Behavior When Context Is Too Large

- Try fitting: instructions + latest user + recent turns.

- If over: summarize older history; keep recent verbatim.

- Slide window until within maxPromptTokens.

- If still over after aggressive summarization:
	- Drop deeper history, or
	- Reject if instructions alone exceed context.