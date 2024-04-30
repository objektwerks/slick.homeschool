package homeschool

import java.time.LocalDateTime

import com.typesafe.config.ConfigFactory

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers


import slick.basic.DatabaseConfig
import slick.jdbc.{H2Profile, JdbcProfile}

final class RepositoryTest extends AnyFunSuite with Matchers:
  test("repository"):
    val config = DatabaseConfig.forConfig[JdbcProfile]("test", ConfigFactory.load("test.conf"))
    val repository = new Repository(config, H2Profile)
    repository.createSchema()
    repository.dropSchema()
    repository.close()
