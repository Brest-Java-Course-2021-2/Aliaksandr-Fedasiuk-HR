## Data Manipulation)

```
SELECT * FROM DEPT


SELECT * FROM emp ORDER BY firstname


SELECT * FROM emp ORDER BY salary, firstname


SELECT *, salary*10 FROM emp ORDER BY firstname


SELECT *, salary*10 as extra_salary FROM emp ORDER BY firstname


SELECT firstname FROM emp ORDER BY firstname


SELECT DISTINCT firstname FROM emp ORDER BY firstname


SELECT firstname, COUNT(*) FROM emp GROUP BY firstname


SELECT firstname, COUNT(*) FROM emp GROUP BY firstname HAVING COUNT(*) > 1


SELECT MAX(emp_id), MAX(firstname)  MAX FROM emp
UNION 
SELECT 5, 'BlaBlaBla' FROM emp


select S.* from
(SELECT MAX(emp_id) as ID, MAX(firstname)  MAX FROM emp
UNION
SELECT 5  as ID, 'BlaBlaBla' FROM emp) as S
ORDER BY S.ID DESC


SELECT LENGTH(firstname) FROM emp


SELECT firstname FROM emp LIMIT 5 OFFSET 0


SELECT d.dept_id, d.dept_name, e.firstname || ' ' || e.lastname AS fio 
  FROM  dept AS d
JOIN emp AS e ON (d.dept_id = e.dept_id)

-- error sample
SELECT d.dept_id, d.dept_name, AVG(e.sal)
  FROM  dept AS d
LEFT JOIN emp AS e ON (d.dept_id = e.dept_id)


SELECT d.dept_id, d.dept_name, AVG(e.salary)
  FROM  dept AS d
LEFT JOIN emp AS e ON (d.dept_id = e.dept_id)
GROUP BY d.dept_id, d.dept_name
ORDER BY d.dept_name
```
