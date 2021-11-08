DROP TABLE IF EXISTS department;
CREATE TABLE department(
    departmentId INT NOT NULL AUTO_INCREMENT,
    departmentName VARCHAR(20) NOT NULL UNIQUE,
    PRIMARY KEY (departmentId)
);