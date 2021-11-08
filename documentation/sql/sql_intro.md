[DB Engines rankings](https://db-engines.com/en/ranking_trend)

[h2 database](https://www.h2database.com/html/main.html)

```
cd ~/Downloads/
wget https://h2database.com/h2-2019-10-14.zip
mkdir ~/db
unzip h2-2019-10-14.zip -d ~/db
cd ~/db/h2/bin
java -jar h2-1.4.200.jar
```

[SQL Grammar](www.h2database.com/html/grammar.html)

## Sample HR schema
```
-- Schema HR
drop table emp; 
drop table dept;

-- department
CREATE TABLE dept (
  dept_id   INTEGER NOT NULL AUTO_INCREMENT,
  dept_name VARCHAR(45) NULL,
  PRIMARY KEY (dept_id)
);

-- employee
CREATE TABLE emp (
  emp_id    INTEGER NOT NULL AUTO_INCREMENT,
  firstname VARCHAR(45) NULL,
  lastname  VARCHAR(45) NULL,
  dept_id   INTEGER NOT NULL,
  salary    DECIMAL(10,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (emp_id),
  CONSTRAINT emp_to_dept_fk
        FOREIGN KEY (dept_id)
        REFERENCES dept (dept_id)
        ON DELETE NO ACTION
        ON UPDATE NO ACTION
);

```

## Sampla data
```
INSERT INTO dept (dept_id, dept_name) VALUES (1, 'DEV');
INSERT INTO dept (dept_id, dept_name) VALUES (2, 'ACCOUNTING');
INSERT INTO dept (dept_id, dept_name) VALUES (3, 'MARKETING');
INSERT INTO dept (dept_id, dept_name) VALUES (4, 'HR');

INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (1, 'Ivan', 'Ivanov', 1, 500);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (2, 'Natashia  ', 'Konecny', 1, 350);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (3, 'Kourtney', 'Ostrem', 1, 470);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (5, 'Coletta ', 'Stoltzfus', 2, 600);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (7, 'Alex', 'Sidorov', 2, 650);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (8, 'Sidorenko', 'Aliaksei', 3, 450);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (9, 'Olha', 'Ivanova', 3, 350);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (10, 'Ivan', 'Petrov', 1, 450);
INSERT INTO emp (emp_id, firstname, lastname, dept_id, salary) VALUES (11, 'Alex', 'Petrov', 2, 550);

INSERT INTO emp (firstname, lastname, dept_id, salary) VALUES ('Alex', 'Kyznecov', 1, 455);

COMMIT;
```

## UI tool 
[Dbeaver](https://dbeaver.io/)

