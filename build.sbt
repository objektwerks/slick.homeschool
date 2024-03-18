name := "slick.homeschool"
organization := "objektwerks"
version := "0.1-SNAPSHOT"
scalaVersion := "3.4.1-RC2"
libraryDependencies ++= {
  val slickVersion = "3.5.0"
  Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.h2database" % "h2" % "2.2.224",
    "ch.qos.logback" % "logback-classic" % "1.5.3",
    "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
}
