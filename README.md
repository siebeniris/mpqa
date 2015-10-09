# The Scala/Java API for MPQA 3.0
The **Scala/Java API for MPQA 3.0** is the simplest way to incorporate the [MPQA 3.0](http://mpqa.cs.pitt.edu/corpora/mpqa_corpus/) corpus into your project. It is designed to be simple and intuitive. The API is most friendly to Scala users, since it is written in Scala. But it also exposes Java-friendly methods with which a Java user can feel comfortable. 

## Installation
### For Scala Users
Simply add the following two lines to the `build.sbt` file of your project. 

```sbt
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "me.yuhuan" %% "mpqa" % "0.0.1-SNAPSHOT"
```

### For Java Users
First, add the following lines in `<project> ... </project>` of your `pom.xml` file:

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


Then, add the following lines in `<dependencies> ... </dependencies>` of your `pom.xml` file:

    <dependency>
        <groupId> me.yuhuan </groupId>
        <artifactId> mpqa_2.11</artifactId>
        <version> 0.0.1-SNAPSHOT </version>
    </dependency>


## Documentation
For ScalaDoc (the machine-generated documentation for classes and methods), please [click here](http://people.cs.pitt.edu/~yuhuan/mpqa-doc/#edu.pitt.mpqa.node.Document).

For the human-written explanation of structure design and tutorials on how to use the API, please [click here](https://github.com/jyuhuan/mpqa/wiki).

## GUI Browser
Looking for the intuitive GUI browser? Please visit the [mpqa-gui repository](https://github.com/jyuhuan/mpqa-gui).
