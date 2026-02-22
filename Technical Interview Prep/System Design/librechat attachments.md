Purpose

- Attachments.backfill() optionally merges attachments into the working message list just before prompt construction.

Public API

- constructor(resendFiles = true)

- backfill(messages, { attachments?, resendFiles? }) → Promise<TMessage[]>

- addImageURLs(message, images[], opts?) → Promise<void> (stub)

- checkVisionRequest(images[]) → void (stub)

How it works

- If resendFiles is false (either instance default or per-call override), it returns messages unchanged.

- Designed as a hook point to:

- Re-inject previously attached files to the latest user message (for vision or retrieval).

- Attach current opts.attachments when present.

- Optionally de-duplicate or normalize file descriptors.

- Current test implementation is a no-op backfill aside from honoring resendFiles.

Integration

- Called inside BaseClient.sendMessage() right after persisting the user message and before ConversationContext.build().

Configuration

- Global default via constructor, request-specific override via opts.resendFiles.

Edge cases and notes

- If there are no prior or new attachments, returns the original list.

- Safe to call even if messages is empty; it simply returns the input.

- Extend to support:

- File metadata fetch (mime, size, display name).

- Vision-specific transformations (thumbnails, signed URLs).

- RAG pre-tagging (mark which messages have file context).