name := "slick.homeschool"
organization := "objektwerks"
version := "0.4-SNAPSHOT"
scalaVersion := "3.8.0-RC5"
libraryDependencies ++= {
  val slickVersion = "3.5.2"
  Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.h2database" % "h2" % "2.4.240",
    "ch.qos.logback" % "logback-classic" % "1.5.22",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wunused:all",
  "-unchecked", "-deprecation"
)
