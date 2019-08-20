name := "InClass"
version := "1.0"
scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
	"com.typesafe.akka" %% "akka-actor" % "2.5.24",
	"com.typesafe.akka" %% "akka-testkit" % "2.5.24" % Test,
	"org.scala-lang.modules" %% "scala-xml" % "1.2.0",
	"org.scalafx" %% "scalafx" % "8.0.192-R14",
	"com.novocode" % "junit-interface" % "0.11" % Test,
	"org.scalactic" %% "scalactic" % "3.0.8",
	"org.scalatest" %% "scalatest" % "3.0.8" % "test"
)


