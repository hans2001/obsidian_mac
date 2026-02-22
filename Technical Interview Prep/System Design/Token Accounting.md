# Token Calculation Methods

## 1. Base Token Counting Method

- Standard counting based on model’s tokenizer.
## 2. Text Token Counting

- Count tokens for raw text messages.
## 3. Content Parts Processing

- Handle multi-part content (system, user, assistant).

# Provider-Specific Token Counting
## OpenAI Provider Example

- Uses `tiktoken` or equivalent tokenizer.
- Includes system + assistant + user roles.
## Anthropic Provider Example

- Uses Claude’s own counting rules.
- Larger context window (up to ~200k tokens).
    
# Token Usage Tracking

## 1. Message Token Count
- Track tokens per message.
## 2. Token Count Map
- Maintain mapping of message → token count.
## 3. Usage Recording
- Record total tokens for billing + monitoring.

# Token Calculation Flow
### Step 1: Count Input Tokens
- Calculate tokens for system + user messages.
### Step 2: Calculate Total Input Tokens
- Aggregate across entire conversation window.
### Step 3: Count Output Tokens
- Add assistant’s generated tokens.

 ![[Screenshot 2025-09-03 at 12.30.55 PM.png]]