package homeschool

import java.time.LocalDateTime

sealed trait Entity

object Entity:
  given Ordering[Teacher] = Ordering.by[Teacher, String](t => t.name)
  given Ordering[Student] = Ordering.by[Student, String](s => s.name)
  given Ordering[Grade] = Ordering.by[Grade, Int](g => g.grade).reverse
  given Ordering[School] = Ordering.by[School, String](s => s.name)
  given Ordering[Category] = Ordering.by[Category, String](c => c.name)
  given Ordering[Course] = Ordering.by[Course, String](c => c.name)
  given Ordering[Assignment] = Ordering.by[Assignment, String](a => a.assigned)

final case class Teacher(id: Int = 0,
                         name: String,
                         email: String,
                         timestamp: String = LocalDateTime.now.toString)

final case class Student(id: Int = 0,
                         name: String,
                         email: String,
                         born: String,
                         timestamp: String = LocalDateTime.now.toString)

final case class Grade(id: Int = 0,
                       studentId: Int,
                       grade: Int,
                       started: String = LocalDateTime.now.toString,
                       completed: String = LocalDateTime.now.plusMonths(6).toString,
                       timestamp: String = LocalDateTime.now.toString)

final case class School(id: Int = 0,
                        name: String,
                        website: Option[String] = None,
                        timestamp: String = LocalDateTime.now.toString)

final case class Category(name: String,
                          timestamp: String = LocalDateTime.now.toString)

final case class Course(id: Int = 0,
                        schoolId: Int,
                        category: String,
                        name: String,
                        website: Option[String] = None,
                        timestamp: String = LocalDateTime.now.toString)

final case class Assignment(id: Int = 0,
                            studentId: Int,
                            gradeId: Int,
                            courseId: Int,
                            description: String,
                            assigned: String = LocalDateTime.now.toString,
                            completed: String = LocalDateTime.now.plusHours(4).toString,
                            score: Double = 0.0,
                            timestamp: String = LocalDateTime.now.toString)