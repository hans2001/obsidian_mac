Purpose
- addInstructions(messages, instructions, beforeLast?) injects a system instruction either at the very top (default) or immediately before the last message.

API
- addInstructions(messages, { content, tokenCount? }, beforeLast=false) → TMessage[]

	- When instructions is falsy or lacks content, returns messages unchanged.
	- Creates a synthetic system TMessage with:
		- messageId: 'instructions', conversationId: 'instructions', parentMessageId: null, role: 'system', sender: 'AI'.
		
		- text: instructions.content
		
		- tokenCount: instructions.tokenCount ?? 0

- If beforeLast=false (default), returns [sys, ...messages].

- If beforeLast=true, inserts sys before the last element while preserving earlier messages.

How it fits the flow

- ConversationContext.build():

- Calls addInstructions(messages, instructions) prior to truncation.

- The inserted instruction’s token count participates in budget calculations (instructionsTokens).

When to use beforeLast=true

- To keep the instruction close to the latest user turn when providers weigh proximity.

- Useful when instructions are highly contextual to the last turn rather than global.

Edge cases

- With 0 or 1 message, insertion before last degenerates to prepend or tail-insert as implemented; order is still consistent.

- Ensure tokenCount estimates for instructions are accurate—they directly reduce available context budget.

Authoring guidance

- Keep instructions concise, consistent, and stable across turns unless deliberately changing behavior.

- Prefer explicit, verifiable directives (formatting, safety, persona) over vague style tips.