link: https://python.langchain.com/docs/tutorials/

## PromptTemplate
a template string with placeholders for dynamic variables.

**Example** 
a final prompt is generated based on the dynamic runtime variables that is supplied! 
( supply a dictionary or object with key-value pairs that corresponds to the placeholders! )
```python
from langchain.prompts import PromptTemplate

template = "Answer the question based on the context: {context}\nQuestion: {question}"
prompt = PromptTemplate(template=template, input_variables=["context", "question"])

final_prompt = prompt.format(context="LLM models are evolving rapidly.", question="What is LangChain?")
# final_prompt: "Answer the question based on the context: LLM models are evolving rapidly.
# Question: What is LangChain?"
```
benefits: allow rapid prototyping and tuning! 

## RouterChain
a dispatcher chain, that routes incoming query to the proper sub-chain for handling
### Steps
setup several subchains, each with a dedicated promptTemplate and specialize logic( summarization, compliance related)
leverages the LLM itself to determine which sub-chain we should use

```js
const routerChain = new RouterChain({
  // The router takes a query and outputs the name of the chain to route to
  prompt: "Decide whether the query is for compliance Q&A, summarization, or general queries. Query: {query}",
  candidateChains: {
    compliance: complianceChain,
    summarization: summarizationChain,
    general: generalChain
  }
});
```
benefits: improvement in answer accuracy! 

## Memory Module 
stores context across interactions -> remember previous interactions 
- **ConversationBufferMemory:** Stores a buffer of past interactions.
- **Vector-based Memory:** Uses vector embeddings to store and retrieve contextual data more semantically. For example, using a conversation buffer
```js
const memory = new ConversationBufferMemory({ returnMessages: true });
const chainWithMemory = new LLMChain({
  llm: yourLLM,
  prompt: promptTemplate,
  memory: memory
});
```
benefits: allow system to recall previous details and ensure response are coherent! 

# Librechat 
CustomAgent.js
```js
const CustomAgent = require('./CustomAgent');
const { CustomOutputParser } = require('./outputParser');
const { AgentExecutor } = require('langchain/agents');
const { LLMChain } = require('langchain/chains');
const { BufferMemory, ChatMessageHistory } = require('langchain/memory');
const {
	ChatPromptTemplate,
	SystemMessagePromptTemplate,
	HumanMessagePromptTemplate,
} = require('@langchain/core/prompts');

const initializeCustomAgent = async ({
tools,
model,
pastMessages,
customName,
customInstructions,
currentDateString,
...rest
}) => {
	let prompt = CustomAgent.createPrompt(tools, { currentDateString, model: model.modelName });
	if (customName) {
		prompt = `You are "${customName}".\n${prompt}`;
	}
	if (customInstructions) {
		prompt = `${prompt}\n${customInstructions}`;
	}
	
	const chatPrompt = ChatPromptTemplate.fromMessages([
		new SystemMessagePromptTemplate(prompt),
		HumanMessagePromptTemplate.fromTemplate(`{chat_history}
		Query: {input}
		{agent_scratchpad}`),
	]);
	
	const outputParser = new CustomOutputParser({ tools });
	
	const memory = new BufferMemory({
		llm: model,
		chatHistory: new ChatMessageHistory(pastMessages),
		memoryKey: 'chat_history',
		humanPrefix: 'User',
		aiPrefix: 'Assistant',
		inputKey: 'input',
		outputKey: 'output',
	});
	
	const llmChain = new LLMChain({
		prompt: chatPrompt,
		llm: model,
	});
	
	const agent = new CustomAgent({
		llmChain,
		outputParser,
		allowedTools: tools.map((tool) => tool.name),
		});
	
	return AgentExecutor.fromAgentAndTools({ agent, tools, memory, ...rest });
};
module.exports = initializeCustomAgent;
```

# context aware memory modules
**buffer memory** 
storing conversation history as a list of messages
stores messages in a buffer 
initialize with chat message history (with past messages) 
memory retained? 

**conversationSummaryBuffer Memory**
extension of buffer memory that summarize older message to save tokens
-> for longer conversations

# chat and prompt tools: 
**LLM chain** 
pass prompt to LLM and returns the response
reasoning engine 

**chatPromptTemplate** 
system and human messages
agent instructions ?

# text processing and agent tools
**tokentextsplitter**
split text in to chunk based on token count

**agentExecutor**
orchestrates interaction between agent and tis tools
handle agent loop: observe, think, act

**func fromAgentTools :**
connect agent, tools and memory
handle execution loop 

# LibreChat
#### Engineered modular LLM workflows with LangChain, combining PromptTemplate, RouterChain, and context-aware memory modules to enable dynamic task routing and improve answer accuracy by 35% across compliance Q&A and summarization.
```js
// Create specialized chains with dedicated memory modules
const extractionChain = new LLMChain({
  llm: model,
  prompt: extractionPrompt,
  memory: new VectorStoreRetrieverMemory({
    vectorStoreRetriever,
    memoryKey: "document_sections"
  })
});

const summarizationChain = new LLMChain({
  llm: model,
  prompt: summarizationPrompt,
  memory: new ConversationSummaryBufferMemory({
    llm: model,
    maxTokenLimit: 2000
  })
});

const generalChain = new LLMChain({
  llm: model,
  prompt: conversationPrompt,
  memory: new BufferMemory()
});

// Create router chain
const routerChain = new RouterChain({
  routerLLM: model,
  chains: {
    "extraction": extractionChain,
    "summarization": summarizationChain,
    "general": generalChain
  }
});
```
