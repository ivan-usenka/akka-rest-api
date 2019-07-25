name := "akka-rest-api"

version := "0.1"

scalaVersion := "2.13.0"

libraryDependencies ++= {
  val akkaVersion = "2.5.23"
  val akkaHttp = "10.1.9"
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core"  % akkaHttp,
    "com.typesafe.akka" %% "akka-http"       % akkaHttp,
    "com.typesafe.play" %% "play-ws-standalone-json"       % "2.0.7",
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.2.3",
    "de.heikoseeberger" %% "akka-http-play-json" % "1.27.0",
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion   % "test"
  )
}

mainClass in assembly := Some("com.havi.merger.MergerApplication")