CREATE TABLE charms_class (
    student_name varchar(255),
    charms_grade varchar(255)
);
INSERT INTO charms_class (student_name, charms_grade)
VALUES
    ('Harry', 'C'),
    ('Ron', 'D'),
    ('Hermione', 'A'),
    ('Luna', 'B'),
    ('Neville', 'B')
;
CREATE TABLE potions_class (
    student_name varchar(255),
    potions_grade varchar(255)
);
INSERT INTO potions_class (student_name, potions_grade)
VALUES 
    ('Harry', 'A'),
    ('Ron', 'C'),
    ('Hermione', 'B'),
    ('Ginny', 'A'),
    ('Draco', 'D')
;

-- INNER JOIN and JOIN both are same
select * from charms_class INNER JOIN potions_class on charms_class.student_name=potions_class.student_name;
select * from charms_class JOIN potions_class on charms_class.student_name=potions_class.student_name;

select * from charms_class LEFT JOIN potions_class on charms_class.student_name=potions_class.student_name;
select * from charms_class RIGHT JOIN potions_class on charms_class.student_name=potions_class.student_name;
-- No Full Join in MySQL. for that use UNION on LEFT JOIN and RIGHT JOIN as below
select * from charms_class LEFT JOIN potions_class on charms_class.student_name=potions_class.student_name UNION select * from charms_class RIGHT JOIN potions_class on charms_class.student_name=potions_class.student_name;

select student_name, potions_grade from potions_class union all select student_name, charms_grade from charms_class ;