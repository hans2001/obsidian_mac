The goal of the review is to:
- Confirm your knowledge of the SQL SELECT command.  
- Practice explaining the workings of the SELECT command and defending your SQL coding decisions.  
- Identify and discuss alternative solutions.  

### What to Expect During the Review
You will sign up for a 20-minute session with your TA. During the session you will:

1. The student chooses a query to review,  the query must have a subquery or at least 3 tables in the solution (5 minutes)  
    
    Walk through one of your SQL SELECT  statements for one of the problem statements.
    
	- Highlight how your code solves the problem and how your verification query ensures the correct solution.

1. Discuss Your Implementation (2 minutes)

	- The TA will ask questions such as: 
	
	- Why did you choose these subclauses?
	    
	- What are the levels of the query and how do they work together to solve the problem?

5. Reflect on Improvements (3 minutes)
	    
	- Identify an alternative solution.
	    
	- Discuss any challenges you faced translating the text to a SQL SELECT command.


5. The TA will describe another  question to answer on the schema (10 minutes)

- Identify the steps needed to solve the problem

- Identify the needed data sources
    
- Identify the needed SELECT subclauses.
    
- Create the SQL statement.
    
- Describe the result set.
    

**1.      (5 points) For each character role (tuple in the role_trimmed table), return the character name and the books they appear in. Each returned row contains a character name and a book id, and a book title.  Order the results in ascending order using the book id.**


current:
SELECT r.name, b.book_number, b.title
FROM role_trimmed r
JOIN role_in_book rib ON r.id = rib.role_id
JOIN book b ON rib.book_id = b.book_number
ORDER BY b.book_number ASC;

alternate: 
SELECT r.name, b.book_number, b.title
FROM role_in_book rib
JOIN role_trimmed r ON r.id = rib.role_id
JOIN book b        ON b.book_number = rib.book_id
ORDER BY b.book_number, r.name;


or  (dedup)
SELECT DISTINCT r.name, b.book_number, b.title
FROM role_in_book rib
JOIN role_trimmed r ON r.id = rib.role_id
JOIN book b        ON b.book_number = rib.book_id
ORDER BY b.book_number, r.name;

nope:
- Using `LEFT JOIN` accidentally (brings characters with _no_ books—wrong for Q1).
- Ordering by `title` instead of `book_number` (not per spec).
- Hidden duplicates from bad data—explain when you’d add `DISTINCT`.