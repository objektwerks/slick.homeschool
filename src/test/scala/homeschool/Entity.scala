package homeschool

import java.time.LocalDateTime

sealed trait Entity

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