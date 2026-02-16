
## Date: 16/02/2026
# MySQL Practice Report

Today,I created my own database and worked on different SQL operations to strengthen my understanding of how databases function in real-world applications. During this practice session, I explored CRUD operations, joins, aggregate functions, views, stored procedures, triggers, transactions, indexing, and basic security management.

```sql
-- Creating and selecting database
CREATE DATABASE office_db;
USE office_db;

-- Creating tables
CREATE TABLE employees (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    salary DECIMAL(10,2),
    dept_id INT
);

CREATE TABLE departments (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(50)
);

-- Inserting records
INSERT INTO departments (dept_name) VALUES ('IT');
INSERT INTO employees (name, salary, dept_id) VALUES ('Rahul', 45000.00, 1);

-- Retrieving data
SELECT name, salary FROM employees;

-- Joining tables
SELECT e.name, d.dept_name
FROM employees e
JOIN departments d ON e.dept_id = d.dept_id;

-- Updating records
UPDATE employees SET salary = 48000.00 WHERE emp_id = 1;

-- Deleting records
DELETE FROM employees WHERE emp_id = 1;

-- Using aggregate functions
SELECT COUNT(*) FROM employees;
SELECT MAX(salary) FROM employees;

-- Grouping data
SELECT dept_id, COUNT(*)
FROM employees
GROUP BY dept_id;

-- Creating a view
CREATE VIEW emp_view AS
SELECT name, salary FROM employees;

-- Creating stored procedure
DELIMITER //
CREATE PROCEDURE GetAllEmployees()
BEGIN
    SELECT * FROM employees;
END //
DELIMITER ;

-- Creating trigger
CREATE TRIGGER before_salary_insert
BEFORE INSERT ON employees
FOR EACH ROW
SET NEW.salary = IF(NEW.salary < 0, 0, NEW.salary);

-- Using transaction
START TRANSACTION;
UPDATE employees SET salary = 50000 WHERE emp_id = 1;
COMMIT;

-- Creating index
CREATE INDEX idx_emp_name ON employees(name);

-- Granting permission
GRANT INSERT ON office_db.* TO 'staff_user'@'localhost';
