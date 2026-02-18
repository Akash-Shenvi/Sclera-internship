# SQL Study Report

## Topics Studied:
- Aggregate Functions
- Subqueries
- Views

---

##  Aggregate Functions

Today I studied SQL Aggregate Functions, which are used to perform calculations on multiple rows and return a single summarized result.

### Functions Covered:
- COUNT() – Counts total records
- SUM() – Calculates total sum
- AVG() – Finds average value
- MIN() – Finds minimum value
- MAX() – Finds maximum value

### Key Learnings:
- How aggregate functions work with GROUP BY
- Difference between WHERE and HAVING
- How to filter grouped data using HAVING
- Practical use in reports and data analysis

### Example:
SELECT department, AVG(salary)
FROM employees
GROUP BY department;

---

## 🔎 Subqueries

I explored Subqueries, which are queries written inside another query to retrieve filtered or dependent data.

### Types Covered:
- Single-row subquery
- Multiple-row subquery
- Correlated subquery

### Key Learnings:
- Using subqueries inside WHERE, FROM, and SELECT
- Using operators like IN and EXISTS
- Difference between correlated and non-correlated subqueries
- How subqueries improve data filtering logic

### Example:
SELECT name
FROM employees
WHERE salary > (SELECT AVG(salary) FROM employees);

---

## 👁 Views

I studied Views, which are virtual tables created using SELECT queries.

### Key Concepts:
- Views do not store data physically
- Used to simplify complex queries
- Provide security by restricting data access
- Improve reusability and maintainability

### Example:
CREATE VIEW high_salary_employees AS
SELECT name, salary
FROM employees
WHERE salary > 50000;

---


