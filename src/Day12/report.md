# Database Automation, Indexing & Transaction Management – Learning Report

Today, I worked on implementing database-level automation, indexing strategies, and transaction management in MySQL to improve performance, maintain data integrity, and handle concurrency effectively.

##  Triggers & Events (Database Automation)

I implemented triggers to automate operations before and after INSERT, UPDATE, and DELETE actions. For example, I created a trigger to automatically set the `created_at` timestamp before inserting a new record. I also built an audit trigger to log old and new values whenever user data is updated, helping maintain proper tracking and accountability.

Additionally, I configured scheduled database events to perform automated tasks such as daily cleanup of old log records. This helped me understand how MySQL can execute background operations without manual intervention.

**Learning Outcome:**  
I understood how triggers enforce business rules at the database level and how events help automate recurring maintenance tasks.

---

##  Indexing & Query Optimization

I implemented various indexing techniques to improve query performance and reduce full table scans. I created single-column indexes for faster searching and composite indexes for multi-column filtering. I also explored prefix indexes for long text fields and understood how column order affects composite index efficiency.

I learned how indexes improve `WHERE` filtering, `ORDER BY` sorting, and how covering indexes allow MySQL to fetch results directly from the index without accessing the table. I also studied full-text indexes for efficient searching in large text-based content.

**Learning Outcome:**  
I gained clarity on how indexes internally optimize data retrieval and how proper indexing significantly improves database performance.

---

##  Transactions & Concurrency Management

I practiced transaction control using `START TRANSACTION`, `COMMIT`, and `ROLLBACK` to ensure multiple operations execute as a single logical unit. I implemented row-level locking using `SELECT ... FOR UPDATE` to safely manage concurrent updates.

I studied common concurrency problems such as Dirty Reads, Non-repeatable Reads, and Phantom Reads, and configured different isolation levels including READ COMMITTED, REPEATABLE READ, and SERIALIZABLE. I also understood how deadlocks occur and learned strategies to reduce them by maintaining proper transaction order and minimizing lock duration.

**Learning Outcome:**  
I developed a strong understanding of maintaining data consistency in multi-user environments and how isolation levels and locking mechanisms protect transactional integrity.

---

