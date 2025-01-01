name := "slick.homeschool"
organization := "objektwerks"
version := "0.4-SNAPSHOT"
scalaVersion := "3.6.3-RC1"
libraryDependencies ++= {
  val slickVersion = "3.5.2"
  Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.h2database" % "h2" % "2.3.232",
    "ch.qos.logback" % "logback-classic" % "1.5.15",
    "org.scalatest" %% "scalatest" % "3.2.19" % Test
  )
}
scalacOptions ++= Seq(
  "-Wall",
  "-unchecked", "-deprecation"
)
