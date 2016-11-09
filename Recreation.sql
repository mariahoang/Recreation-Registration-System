-- this will drop the Student table if it already exists
DROP TABLE IF EXISTS student;

-- this will drop the Course table if it already exists
DROP TABLE IF EXISTS course;

-- this will drop the StudentCourse table if it already exists
DROP TABLE IF EXISTS studentcourse;

-- this creates the student table
CREATE TABLE student (
    id int NOT NULL AUTO_INCREMENT,
    firstName varchar(25) NOT NULL,
    lastName varchar(25) NOT NULL,
    age int NOT NULL,
    PRIMARY KEY (id)
);

-- this creates the course table
CREATE TABLE course (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(25) NOT NULL,
    startTime time NOT NULL,
    PRIMARY KEY (id)
);

-- this creates the bridge table for student and course
CREATE TABLE studentcourse (
    studentId int NOT NULL,
    courseId int NOT NULL,
    PRIMARY KEY (studentId,courseId),
    CONSTRAINT student_fk
    FOREIGN KEY (studentId) REFERENCES student(id),
    CONSTRAINT course_fk
    FOREIGN KEY (courseId) REFERENCES course(id)
);

-- the given records of student are inserted into the student table
INSERT INTO student(firstName,lastName,age) VALUES
    ('John','Johnson',10),
    ('Bob','Bobson',11),
    ('Maddie','Maddison',15),
    ('Mary','Molson',12),
    ('Ed','Edison',13),
    ('Mike','Molson',11);

-- the given records of courses are inserted into the course table
INSERT INTO course (name,startTime) VALUES 
    ('Swimming','9:00'),
    ('Tennis','11:00'),
    ('Soccer','15:00');

--  the given records of studentcourse are inserted into the studentcourse table
INSERT INTO studentcourse (studentId,courseId) VALUES

    ((SELECT id FROM student WHERE firstName = 'John' AND lastName = 'Johnson'),(SELECT id FROM course WHERE name = 'Swimming')),
    ((SELECT id FROM student WHERE firstName = 'John' AND lastName = 'Johnson'),(SELECT id FROM course WHERE name = 'Tennis')),
    ((SELECT id FROM student WHERE firstName = 'John' AND lastName = 'Johnson'),(SELECT id FROM course WHERE name = 'Soccer')),

    ((SELECT id FROM student WHERE firstName = 'Bob' AND lastName = 'Bobson'),(SELECT id FROM course WHERE name = 'Swimming')),
    ((SELECT id FROM student WHERE firstName = 'Bob' AND lastName = 'Bobson'),(SELECT id FROM course WHERE name = 'Tennis')),
    
    ((SELECT id FROM student WHERE firstName = 'Maddie' AND lastName = 'Maddison'),(SELECT id FROM course WHERE name = 'Swimming')),
    ((SELECT id FROM student WHERE firstName = 'Maddie' AND lastName = 'Maddison'),(SELECT id FROM course WHERE name = 'Tennis')),
    ((SELECT id FROM student WHERE firstName = 'Maddie' AND lastName = 'Maddison'),(SELECT id FROM course WHERE name = 'Soccer')),

    ((SELECT id FROM student WHERE firstName = 'Mary' AND lastName = 'Molson'),(SELECT id FROM course WHERE name = 'Swimming')),
    ((SELECT id FROM student WHERE firstName = 'Mary' AND lastName = 'Molson'),(SELECT id FROM course WHERE name = 'Soccer')),

    ((SELECT id FROM student WHERE firstName = 'Ed' AND lastName = 'Edison'),(SELECT id FROM course WHERE name = 'Swimming')),
    ((SELECT id FROM student WHERE firstName = 'Ed' AND lastName = 'Edison'),(SELECT id FROM course WHERE name = 'Soccer')),
    
    ((SELECT id FROM student WHERE firstName = 'Mike' AND lastName = 'Molson'),(SELECT id FROM course WHERE name = 'Soccer')),
    ((SELECT id FROM student WHERE firstName = 'Mike' AND lastName = 'Molson'),(SELECT id FROM course WHERE name = 'Tennis'))
;
    