# The Scala/Java API for MPQA 3.0
The **Scala/Java API for MPQA 3.0** is the simplest way to incorporate the [MPQA 3.0](http://mpqa.cs.pitt.edu/corpora/mpqa_corpus/) corpus into your project. It is designed to be simple and intuitive. The API is most friendly to Scala users, since it is written in Scala. But it also exposes Java-friendly methods with which a Java user can feel comfortable. 

## Installation
### For SBT Users
Simply add the following two lines to the `build.sbt` file of your project. 

```sbt
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "me.yuhuan" %% "mpqa" % "0.0.1-SNAPSHOT"
```

### For Maven Users
First, add the following lines in `<project> ... </project>` of your `pom.xml` file:

```xml
    <repositories>
        <repository>
            <id>sonatypeSnapshots</id>
            <name>Sonatype Snapshots</name>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
        </repository>
    </repositories>
```

> **NOTE**: This step is necessary because the MPQA project is not publicly released yet. The MPQA is currently on the Sonatype snapshot repository instead of the Maven Central Repository. In the final release, only the following step will be needed.

Then, add the following lines in `<dependencies> ... </dependencies>` of your `pom.xml` file:

```xml
    <dependency>
        <groupId> me.yuhuan </groupId>
        <artifactId> mpqa_2.11</artifactId>
        <version> 0.0.1-SNAPSHOT </version>
    </dependency>
```

## Documentation
For ScalaDoc (the machine-generated documentation for classes and methods), please [click here](http://people.cs.pitt.edu/~yuhuan/mpqa-doc/#edu.pitt.mpqa.node.Document).

For the human-written explanation of structure design and tutorials on how to use the API, please [click here](https://github.com/jyuhuan/mpqa/wiki).

## GUI Browser
Looking for the intuitive GUI browser? Please visit the [mpqa-gui repository](https://github.com/jyuhuan/mpqa-gui).
