- [x] [[GitHub Actions]]
- [x] [[Docker]]
- [x] [[Docker Compose]]
- [x] [[NordVPN]]
- [x] [[OpenAI SDK]]
- [x] [[Tap  & Go]]
- [x] [[LangChain]]
- [ ] [[RAG API]]
- [x] [[LibreChat]]
	- [x] [[LibreChat Model Client]]
	- [x] [[Token Accounting]]
	- [x] [[LibreChat Token Management]]


i should have detialed doc for each sub module in base client

## Assume user in an active chat session ,where do u store the chat messages? do we have to fetch the mongodb everytime for each user message> or u have a temp storage for that, when do u persist that message to the database? what is the temporary storage?

so the current arch does not have temp storage, we can say we use LRU (hash amp with maximum number of keys ) to maintain the active conversation histories , conversationID map to messages
whenever the message proceed, udpate the message ,and it should be move to end to indicate it is the most recently used conversation!

