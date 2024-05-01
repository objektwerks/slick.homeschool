package homeschool

import java.sql.Timestamp
import java.time.LocalDateTime

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class Repository(val config: DatabaseConfig[JdbcProfile],
                 val profile: JdbcProfile,
                 val awaitDuration: Duration = 1.second) {
  import profile.api._

  val schema = teachers.schema ++ students.schema ++ grades.schema ++ schools.schema ++ categories.schema ++ courses.schema ++ assignments.schema
  val db = config.db

  def await[T](action: DBIO[T]): T = Await.result(db.run(action), awaitDuration)

  def exec[T](action: DBIO[T]): Future[T] = db.run(action)

  def close() = db.close()

  def createSchema() = await(DBIO.seq(schema.create))

  def dropSchema() = await(DBIO.seq(schema.drop))

  case class Teacher(id: Int = 0, name: String, email: String, timestamp: String = LocalDateTime.now.toString)
  class Teachers(tag: Tag) extends Table[Teacher](tag, "teachers") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email", O.Unique)
    def timestamp = column[LocalDateTime]("timestamp")
    def * = (id.?, name, email, timestamp).mapTo[Teacher]
  }
  object teachers extends TableQuery(new Teachers(_)) {
    val compiledFind = Compiled { name: Rep[String] => filter(_.name === name) }
    val compiledList = Compiled { sortBy(_.name.asc) }
    def save(teacher: Teacher) = (this returning this.map(_.id)).insertOrUpdate(teacher)
    def find(name: String) = compiledFind(name).result.headOption
    def list() = compiledList.result
  }

  case class Student(id: Int = 0, name: String, email: String, born: String, timestamp: String = LocalDateTime.now.toString)
  class Students(tag: Tag) extends Table[Student](tag, "students") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email", O.Unique)
    def born = column[LocalDateTime]("born")
    def timestamp = column[LocalDateTime]("timestamp")
    def * = (id.?, name, email, born, timestamp).mapTo[Student]
  }
  object students extends TableQuery(new Students(_)) {
    val compiledFind = Compiled { name: Rep[String] => filter(_.name === name) }
    val compiledList = Compiled { sortBy(_.name.asc) }
    def save(student: Student) = (this returning this.map(_.id)).insertOrUpdate(student)
    def find(name: String) = compiledFind(name).result.headOption
    def list() = compiledList.result
  }

  case class Grade(id: Int = 0, studentId: Int, grade: Int, started: LocalDateTime = LocalDateTime.now, completed: LocalDateTime = LocalDateTime.now.plusMonths(6), timestamp: LocalDateTime = LocalDateTime.now)
  class Grades(tag: Tag) extends Table[Grade](tag, "grades") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def studentId = column[Int]("student_id")
    def grade = column[Int]("grade")
    def started = column[LocalDateTime]("started")
    def completed = column[LocalDateTime]("completed")
    def timestamp = column[LocalDateTime]("timestamp")
    def * = (id, studentId, grade, started, completed, timestamp) <> (Grade.tupled, Grade.unapply)
    def studentFk = foreignKey("student_fk", studentId, TableQuery[Students])(_.id)
  }
  object grades extends TableQuery(new Grades(_)) {
    val compiledListByStudent = Compiled { studentId: Rep[Int] => filter(_.studentId === studentId).sortBy(_.grade.asc) }
    def save(grade: Grade) = (this returning this.map(_.id)).insertOrUpdate(grade)
    def list(studentId: Int) = compiledListByStudent(studentId).result
  }

  case class School(id: Int = 0, name: String, website: Option[String] = None, timestamp: LocalDateTime = LocalDateTime.now)
  class Schools(tag: Tag) extends Table[School](tag, "schools") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name", O.Unique)
    def website = column[Option[String]]("website")
    def timestamp = column[LocalDateTime]("timestamp")
    def * = (id, name, website, timestamp) <> (School.tupled, School.unapply)
  }
  object schools extends TableQuery(new Schools(_)) {
    val compiledList = Compiled { sortBy(_.name.asc) }
    def save(school: School) = (this returning this.map(_.id)).insertOrUpdate(school)
    def list() = compiledList.result
  }

  case class Category(name: String, timestamp: LocalDateTime = LocalDateTime.now)
  class Categories(tag: Tag) extends Table[Category](tag, "categories") {
    def name = column[String]("name", O.PrimaryKey)
    def timestamp = column[LocalDateTime]("timestamp")
    def * = (name, timestamp) <> (Category.tupled, Category.unapply)
  }
  object categories extends TableQuery(new Categories(_)) {
    val compiledFindByName = Compiled { name: Rep[String] => filter(_.name === name) }
    val compiledList = Compiled { sortBy(_.name.asc) }
    def add(category: Category) = this += category
    def update(name: String, modified: Category) = compiledFindByName(name).update(modified)
    def list() = compiledList.result
  }

  case class Course(id: Int = 0, schoolId: Int, category: String, name: String, website: Option[String] = None, timestamp: LocalDateTime = LocalDateTime.now)
  class Courses(tag: Tag) extends Table[Course](tag, "courses") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def schoolId = column[Int]("school_id")
    def category = column[String]("category")
    def name = column[String]("name")
    def website = column[Option[String]]("website")
    def timestamp = column[LocalDateTime]("timestamp")
    def * = (id, schoolId, category, name, website, timestamp) <> (Course.tupled, Course.unapply)
    def schoolFk = foreignKey("school_fk", schoolId, TableQuery[Schools])(_.id)
    def categoryFk = foreignKey("category_fk", category, TableQuery[Categories])(_.name)
  }
  object courses extends TableQuery(new Courses(_)) {
    val compiledListBySchool = Compiled { schoolId: Rep[Int] => filter(_.schoolId === schoolId).sortBy(_.name.asc) }
    def save(course: Course) = (this returning this.map(_.id)).insertOrUpdate(course)
    def list(schoolId: Int) = compiledListBySchool(schoolId).result
  }

  case class Assignment(id: Int = 0, studentId: Int, gradeId: Int, courseId: Int,
                        description: String,
                        assigned: LocalDateTime = LocalDateTime.now,
                        completed: LocalDateTime = LocalDateTime.now.plusHours(4),
                        score: Double = 0.0, timestamp: LocalDateTime = LocalDateTime.now)
  class Assignments(tag: Tag) extends Table[Assignment](tag, "assignments") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def studentId = column[Int]("student_id")
    def gradeId = column[Int]("grade_id")
    def courseId = column[Int]("course_id")
    def description = column[String]("description")
    def assigned = column[LocalDateTime]("assigned")
    def completed = column[LocalDateTime]("completed")
    def score = column[Double]("score")
    def timestamp = column[LocalDateTime]("timestamp")
    def * = (id, studentId, gradeId, courseId, description, assigned, completed, score, timestamp) <> (Assignment.tupled, Assignment.unapply)
    def studentFk = foreignKey("student_assignment_fk", studentId, TableQuery[Students])(_.id)
    def gradeFk = foreignKey("grade_assignment_fk", gradeId, TableQuery[Grades])(_.id)
    def courseFK = foreignKey("course_assignment_fk", courseId, TableQuery[Courses])(_.id)
  }
  object assignments extends TableQuery(new Assignments(_)) {
    val compiledListByStudentGradeCourse = Compiled { (studentId: Rep[Int], gradeId: Rep[Int], courseId: Rep[Int]) =>
      filter(a => a.studentId === studentId && a.gradeId === gradeId && a.courseId === courseId).sortBy(_.assigned.asc) }
    val compiledCalculateScore = Compiled { (studentId: Rep[Int], gradeId: Rep[Int], courseId: Rep[Int]) =>
      filter(a => a.studentId === studentId && a.gradeId === gradeId && a.courseId === courseId).map(_.score).sum }
    def save(assignment: Assignment) = (this returning this.map(_.id)).insertOrUpdate(assignment)
    def list(studentId: Int, gradeId: Int, courseId: Int) = compiledListByStudentGradeCourse( (studentId, gradeId, courseId) ).result
    def calculateScore(studentId: Int, gradeId: Int, courseId: Int) = compiledCalculateScore( (studentId, gradeId, courseId) ).result
  }
}
