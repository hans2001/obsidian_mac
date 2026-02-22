```sql 
SELECT first_name, last_name
FROM employees
WHERE department = 'Sales';
```

**Inner join**
Returns rows with matching values in both tables.
```sql
SELECT e.employee_id, e.first_name, d.department_name
FROM employees e
INNER JOIN departments d ON e.department_id = d.department_id;
```

**Left join**
Returns all rows from the left table and matching rows from the right table. If no match exists, the result is NULL on the right side.
```sql
SELECT c.customer_name, o.order_date, o.amount
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id;
```
return all customers, even some has no orders! 

**Group by**
Groups rows that have the same values in specified columns, enabling the use of aggregate functions (like COUNT, SUM, AVG, MIN, MAX).
To list each customer with the total amount of their orders (including those with no orders):
```sql
SELECT 
  c.customer_id,
  c.customer_name,
  c.city,
  COALESCE(SUM(o.amount), 0) AS total_amount
FROM customers c
LEFT JOIN orders o ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name, c.city
HAVING SUM(o.amount) IS NOT NULL -- optional filter, if needed
ORDER BY total_amount DESC;
```
**COALESCE(SUM(o.amount), 0):** Returns 0 instead of NULL when a customer has no orders.
**GROUP BY:** Groups the results by customer details.
**ORDER BY:** Sorts customers by the total order amount in descending order.

**Subqueries**
```sql
SELECT first_name, last_name
FROM employees
WHERE department_id = (
    SELECT department_id FROM departments WHERE department_name = 'Sales'
);
```

## Advanced
You might want to see each customer and the total amount they have spent, including customers with no orders
```sql
SELECT 
  c.customer_id,
  c.customer_name,
  COALESCE(SUM(o.order_total), 0) AS total_spent
FROM customers c
LEFT JOIN orders o 
  ON c.customer_id = o.customer_id
GROUP BY c.customer_id, c.customer_name
ORDER BY total_spent DESC;
```
**LEFT JOIN** ensures all customers are listed.
**SUM** aggregates the order totals, and **COALESCE** converts `NULL` values (from customers with no orders) to 0.

Consider a scenario where you also want to include information about sales representatives from a third table, but only if available:
```sql
SELECT 
  c.customer_id,
  c.customer_name,
  o.order_id,
  o.order_total,
  s.rep_name
FROM customers c
INNER JOIN orders o 
  ON c.customer_id = o.customer_id
LEFT JOIN sales_reps s 
  ON c.sales_rep_id = s.rep_id;
```
**INNER JOIN** between `customers` and `orders` ensures you only get customers who placed orders.

**LEFT JOIN** with `sales_reps` means that if a customer isn’t assigned a sales rep, the `rep_name` field will be NULL, but the row still appears.