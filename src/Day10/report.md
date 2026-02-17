# SQL Joins & Data Manipulation Report

## Relational Data Retrieval Using SQL Joins

Implemented relational data retrieval across multiple tables using various SQL join techniques. Applied different join strategies based on data requirements and optimized queries for structured data relationships.

---

## Inner Join

Used to retrieve matching records from two tables.

```sql
SELECT c.name, o.order_id
FROM customers c
INNER JOIN orders o
ON c.id = o.customer_id;
```

---

## Joining Across Databases

Queried tables across different databases.

```sql
SELECT *
FROM db1.customers c
JOIN db2.orders o
ON c.id = o.customer_id;
```

---

## Self Join

Joined a table with itself to represent hierarchical relationships.

```sql
SELECT e.name, m.name AS manager
FROM employees e
JOIN employees m
ON e.manager_id = m.id;
```

---

## Joining Multiple Tables

Combined data from more than two tables.

```sql
SELECT o.order_id, c.name, p.product_name
FROM orders o
JOIN customers c ON o.customer_id = c.id
JOIN products p ON o.product_id = p.id;
```

---

## Compound Join Conditions

Applied multiple conditions in join.

```sql
SELECT *
FROM orders o
JOIN order_items oi
ON o.id = oi.order_id
AND o.customer_id = oi.customer_id;
```

---

## Implicit Join Syntax

Used older join style with WHERE clause.

```sql
SELECT *
FROM customers c, orders o
WHERE c.id = o.customer_id;
```

---

## Outer Joins

Retrieved unmatched records using outer joins.

### Left Join

```sql
SELECT c.name, o.order_id
FROM customers c
LEFT JOIN orders o
ON c.id = o.customer_id;
```

### Right Join

```sql
SELECT c.name, o.order_id
FROM customers c
RIGHT JOIN orders o
ON c.id = o.customer_id;
```

---

## Outer Join Between Multiple Tables

```sql
SELECT *
FROM customers c
LEFT JOIN orders o ON c.id = o.customer_id
LEFT JOIN payments p ON o.id = p.order_id;
```

---

## Self Outer Join

```sql
SELECT e.name, m.name AS manager
FROM employees e
LEFT JOIN employees m
ON e.manager_id = m.id;
```

---

## USING Clause

Simplified join condition when column names matched.

```sql
SELECT *
FROM orders
JOIN customers USING (customer_id);
```

---

## Natural Join

Automatically joined tables based on common column names.

```sql
SELECT *
FROM orders
NATURAL JOIN customers;
```

---

## Cross Join

Produced Cartesian product of tables.

```sql
SELECT *
FROM customers
CROSS JOIN products;
```

---

## Union

Combined results from multiple SELECT statements.

```sql
SELECT name FROM customers
UNION
SELECT name FROM suppliers;
```

---

# Data Manipulation Operations (DML)

Implemented data manipulation operations including inserting, updating, deleting, and copying records in relational tables. Applied structured modification techniques to maintain data consistency and integrity.

---

## Column Attributes

Defined column constraints and attributes such as PRIMARY KEY, NOT NULL, AUTO_INCREMENT, and DEFAULT.

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    status VARCHAR(20) DEFAULT 'active'
);
```

---

## Inserting a Single Row

Inserted individual records into a table.

```sql
INSERT INTO users (name)
VALUES ('Alice');
```

---

## Inserting Multiple Rows

Inserted multiple records efficiently in a single query.

```sql
INSERT INTO users (name)
VALUES ('Bob'), ('Charlie'), ('David');
```

---

## Inserting Hierarchical Rows

Handled parent-child relationships using foreign keys.

```sql
INSERT INTO employees (name, manager_id)
VALUES ('John', NULL),
       ('Mike', 1);
```

---

## Creating a Copy of a Table

Created duplicate tables for backup or testing.

```sql
CREATE TABLE users_copy AS
SELECT * FROM users;
```

---

## Updating a Single Row

Modified specific records using conditions.

```sql
UPDATE users
SET name = 'Alice Smith'
WHERE id = 1;
```

---

## Updating Multiple Rows

Updated multiple records using conditional filters.

```sql
UPDATE users
SET status = 'inactive'
WHERE id > 2;
```

---

## Using Subqueries in Updates

Used subqueries to update based on related data.

```sql
UPDATE users
SET status = 'inactive'
WHERE id IN (
    SELECT user_id FROM orders
);
```

---

## Deleting Rows

Removed records safely using conditions.

```sql
DELETE FROM users
WHERE id = 3;
```

---

## Restoring Databases

Restored database backups using command-line tools.

```bash
mysql -u root -p database_name < backup.sql
```
