name := "slick.homeschool"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "2.12.10"
libraryDependencies ++= {
  val slickVersion = "3.2.3"
  Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.h2database" % "h2" % "1.4.197",
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "org.scalatest" %% "scalatest" % "3.2.7" % Test
  )
}