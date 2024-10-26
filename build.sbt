ThisBuild / scalaVersion := "3.3.4"
ThisBuild / organization := "com.jarrahtechnology"
ThisBuild / versionScheme := Some("early-semver")

lazy val root = (project in file("."))
  .settings(
    name := "arbitrary",
    version := "0.1.1",
    githubOwner := "jarrahtech",
    githubRepository := "arbitrary",

    scalacOptions ++= Seq(
      "-encoding", "utf8", // Option and arguments on same line
      "-Xfatal-warnings",  // New lines for each options
      "-deprecation",
      //"-Vprofile-details", "5", "-feature"
    ),

    wartremoverErrors ++= Warts.unsafe,
    wartremoverErrors -= Wart.Var, // using vars to match non-scala functions

    resolvers ++= Resolver.sonatypeOssRepos("public"),
    resolvers += Resolver.githubPackages("jarrahtech"),
    libraryDependencies += "com.jarrahtechnology" %% "jarrah-util" % "0.7.1",
    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
  )
