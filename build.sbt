name := "slick.homeschool"
organization := "objektwerks"
version := "0.2"
scalaVersion := "3.4.2-RC1"
libraryDependencies ++= {
  val slickVersion = "3.5.1"
  Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.h2database" % "h2" % "2.2.224",
    "ch.qos.logback" % "logback-classic" % "1.5.6",
    "org.scalatest" %% "scalatest" % "3.2.18" % Test
  )
}