name := "mpqa"

version := "0.0.14-SNAPSHOT"

organization := "me.yuhuan"

scalaVersion := "2.11.7"

publishMavenStyle := true

isSnapshot := true

scalacOptions in (Compile, doc) += "-diagrams"

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

//region Custom Dependencies
libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4"
libraryDependencies += "net.liftweb" % "lift-json_2.11" % "2.6.2"

resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "me.yuhuan" %% "yutil" % "0.0.4-SNAPSHOT"
//endregion


pomExtra :=
  <url>https://github.com/jyuhuan/mpqa</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>http://opensource.org/licenses/MIT</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jyuhuan/mpqa.git</url>
      <connection>scm:git:git@github.com:jyuhuan/mpqa.git</connection>
    </scm>
    <developers>
      <developer>
        <id>yuhuan</id>
        <name>Yuhuan Jiang</name>
        <url>http://yuhuan.me/</url>
      </developer>
    </developers>
