motivation: 

storyline: 
we first use OPENAI SDK along with nextjs to build the prototype, but it only the SDK support model from open ai, and we will need to allow more model to be selected from, that why we decide to transition to a a micro-service arch and built the platform from scratch, we took a lot of reference from and open source project - Librechat, a generic chat assistant  where we select mongodb to our main database, it stores user chat histories, user credentials, user token management and more. moreover we adopt postgress sql to be the vector database, where it stores files uploaded by the user, and a millisearch service is attached to the sql database to perform semantic and keyword search ,and the result will be used to ground the model response and keep the model in context of previous uploaded files! 


I have 3 tasks
1. to integrate the enterprise grade LLM endpoint that is developed our IT department, the endpoint has additional network requirements and compliance filters compared to other AI providers

we have a baseClient class as the generic AI provider adaptor, which is responsible for connecting  to different modules such as estimating token usage, persist user chat history and token usage to the database, saving model configurations and trim and summarize context within desired tokens. i created a subclass for our LLM endpoint, which is call botBuilder, and i implemented 4 virtual functions for the subclass. 

The first one is sendCompletion method, which is the exactly the palace where we communicate with the endpoint, here we parse the user input from the platform and send a completion request to the LLM endpoint. After we got the result, we parse it to a generic shape that is expected by every AI provider, and return as messages for the current session

getSave options, return the configuration we set for the model( including temperatures, token limit, and model version)

edit the model configurations:  a post endpoint that allow us to modify the config in mongo database

return model id


a single conversation concerns a lots of module ,where we have to first fetch chat history for the current session, including semantic search into the postgresql data store, then we estimate token consumption (stops the seq if token is not enough) with the new user input, and we summarize the part where it does not fit within current token (usually older messages), then we send a completion request to the genai model, and it return the model response, and the actual token consumption, then we persist the actual token consumption and current chat records to the database, and then we send the response to the frontend


2. use prompt template and route chain to improve the response accuracy for compliance and summarization tasks!


actually 