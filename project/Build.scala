import sbt._
import sbt.Keys._

object MyBuild extends Build {
  lazy val project = Project("root", file(".")) settings(
    organization := "com.azavea.geotrellis",
    name := "geoslick",
    version := "0.1.1-SNAPSHOT",

    scalaVersion := "2.10.3",

    scalacOptions ++= Seq("-deprecation", "-unchecked", "-optimize"),

    parallelExecution := false,    

    libraryDependencies ++= Seq(
        "com.typesafe.slick" %% "slick" % "1.0.1",
        "org.slf4j" % "slf4j-nop" % "1.7.5",
        //"postgresql" % "postgresql" % "8.4-702.jdbc4",
        //"postgresql" % "postgresql" % "9.1-901.jdbc4",
        "org.postgresql" % "postgresql" % "9.3-1100-jdbc41",
        "com.vividsolutions" % "jts" % "1.12",
        "org.scalatest" % "scalatest_2.10.0" % "2.0.M5" % "test"
    ),

    resolvers ++= Seq(
      "NL4J Repository" at "http://nativelibs4java.sourceforge.net/maven/",
      "maven2 dev repository" at "http://download.java.net/maven/2",
      "Scala Test" at "http://www.scala-tools.org/repo-reloases/",
      "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/",
      "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository",
      "sonatypeSnapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
      "JAI" at "http://download.osgeo.org/webdav/geotools/"
    ),

    resolvers += Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),

    javaOptions in run += "-Xmx6G",
    // enable forking in run
    fork in run := true,

      publishMavenStyle := true,
      publishTo <<= version { (v: String) =>
          val nexus = "https://oss.sonatype.org/"
          if (v.trim.endsWith("SNAPSHOT"))
            Some("snapshots" at nexus + "content/repositories/snapshots")
          else
            Some("releases" at nexus + "service/local/staging/deploy/maven2")
        },
      
      publishArtifact in Test := false,
      pomIncludeRepository := { _ => false },
      licenses := Seq("BSD" -> url("https://raw.github.com/ahinz/GeoSlick/master/LICENSE")),

      pomExtra := (
<scm>
  <url>git@github.com:ahinz/GeoSlick.git</url>
  <connection>scm:git:git@github.com:ahinz/GeoSlick.git</connection>
</scm>
<developers>
  <developer>
    <id>ahinz</id>
    <name>Adam Hinz</name>
    <url>http://github.com/ahinz/</url>
  </developer>
</developers>
      )
    )
}
