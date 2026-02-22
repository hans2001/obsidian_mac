**Core concepts:** 
single source of truth 
	state of whole app is stored in an object tree within a single store
	easy to track of state changes
state is read-only 
	the only way to change the sate is to emit an action! 
	predictable and traceable changes!
changes are made with pure functions -> reducers
	specify how the state tree is transformed by actions, you write pure functions called reducers.
	predictable -> no side effects


