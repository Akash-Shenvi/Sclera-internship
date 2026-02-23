# Common Table Expression (CTE)

A **Common Table Expression (CTE)** is a temporary named result set that exists only for the duration of a single SQL statement.  
It can be used with `SELECT`, `INSERT`, `UPDATE`, or `DELETE` statements.

---

## General Syntax

```sql
WITH cte_name AS (
    SELECT column1, column2
    FROM table_name
    WHERE condition
)
SELECT *
FROM cte_name;
```

- `WITH` → begins the CTE definition
- `cte_name` → temporary result name
- Query inside `()` → defines the dataset
- Outer query → uses the CTE like a table

---

## Example: Employees Earning Above Average Salary

```sql
WITH avg_salary_cte AS (
    SELECT AVG(salary) AS avg_salary
    FROM employees
)
SELECT e.name, e.salary
FROM employees e
JOIN avg_salary_cte a ON 1=1
WHERE e.salary > a.avg_salary;
```

---

## Why Use CTE?

- Makes complex queries easier to read
- Breaks large queries into logical parts
- Eliminates repeated subqueries
- Useful for recursive logic

---

## Recursive CTE Structure (Important for Interviews)

Used for hierarchical data like manager-employee relationships or tree structures.

```sql
WITH recursive_cte AS (
    -- Base query
    SELECT id, parent_id
    FROM table_name
    WHERE parent_id IS NULL

    UNION ALL

    -- Recursive part
    SELECT t.id, t.parent_id
    FROM table_name t
    JOIN recursive_cte r
        ON t.parent_id = r.id
)
SELECT *
FROM recursive_cte;
```