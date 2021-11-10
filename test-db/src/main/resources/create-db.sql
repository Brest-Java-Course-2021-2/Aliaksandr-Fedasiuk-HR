DROP TABLE IF EXISTS employee;

DROP TABLE IF EXISTS department;

CREATE TABLE department
(
    department_id int NOT NULL auto_increment,
    department_name varchar(50) NOT NULL UNIQUE,
    CONSTRAINT department_pk PRIMARY KEY (department_id)
);

CREATE TABLE employee (
    employee_id int NOT NULL auto_increment,
    firstname varchar(255) NOT NULL,
    lastname varchar(255) NOT NULL,
    email varchar(255) NOT NULL UNIQUE,
    salary int NOT NULL,
    department_id int NOT NULL,
        CONSTRAINT employee_pk PRIMARY KEY (employee_id),
        CONSTRAINT employee_department_fk FOREIGN KEY (department_id) REFERENCES department(department_id)
);
