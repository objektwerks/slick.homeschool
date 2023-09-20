Slick Homeschool
----------------
>Homeschool domain model using Slick, H2 and Scala 3.

Warning
-------
>Slick 3 is still a WIP. Switch back to Scala 2.13.12 and Slick 3.4.1 is you need working Slick code. :)

Object Model
------------
* Teacher (id, name, email, timestamp)
* Student (id, name, email, born, timestamp)
* Grade (id, studentId, grade, started, completed, timestamp)
* School (id, name, website?, timestamp)
* Category (name, timestamp)
* Course (id, schoolId, name, category, website?, timestamp)
* Assignment (id, studentId, gradeId, courseId, description, assigned, completed, score, timestamp)

Relational Model
----------------
* Teacher
* Student 1 ---> * Grade
* School 1 ---> * Course 1 ---> 1 Category
* Assignement 1 ---> 1 Student | Grade | Course

Commands
--------
* Change[T]

Events
------
* Changed[T]

Queries
-------
1. List[T]
2. Calculate Score By Student, Grade and Course across Assignments

Test
----
1. sbt clean test
