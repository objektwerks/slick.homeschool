package homeschool

import java.time.LocalDateTime

sealed trait Entity

final case class Teacher(id: Int = 0,
                         name: String,
                         email: String,
                         timestamp: String = LocalDateTime.now.toString)