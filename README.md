# The Scala/Java API for MPQA 3.0
The **Scala/Java API for MPQA 3.0** is the simplest way to incorporate the [MPQA 3.0](http://mpqa.cs.pitt.edu/corpora/mpqa_corpus/) corpus into your project. It is designed to be simple and intuitive. The API is most friendly to Scala users, since it is written in Scala. But it also exposes Java-friendly methods with which a Java user can feel comfortable. 

## Before You Download 

By requesting and downloading the MPQA Corpus, the user agrees to the following:

> The annotations in this data collection are copyrighted by the MITRE Corporation. User acknowledges and agrees that: (i) as between User and MITRE, MITRE owns all the right, title and interest in the Annotated Content, unless expressly stated otherwise; (ii) nothing in this Agreement shall confer in User any right of ownership in the Annotated Content; and (iii) User is granted a non-exclusive, royalty free, worldwide license (with no right to sublicense) to use the Annotated Content solely for academic and research purposes. This Agreement is governed by the law of the Commonwealth of Massachusetts and User agrees to submit to the exclusive jurisdiction of the Massachusetts courts.

> **NOTE**: The textual news documents annotated in this corpus have been collected from a wide range of sources and are not copyrighted by the MITRE Corporation. The user acknowledges that the use of these news documents is restricted to research and/or academic purposes only.

## Installation
### For SBT Users
Simply add the following two lines to the `build.sbt` file of your project. 

```sbt
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "me.yuhuan" %% "mpqa" % "0.0.6-SNAPSHOT"
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
        <version> 0.0.6-SNAPSHOT </version>
    </dependency>
```

## Documentation
For ScalaDoc (the machine-generated documentation for classes and methods), please [click here](http://people.cs.pitt.edu/~yuhuan/mpqa-doc/#edu.pitt.mpqa.node.Document).

For the human-written explanation of structure design and tutorials on how to use the API, please [click here](https://github.com/jyuhuan/mpqa/wiki).

## GUI Browser
Looking for the intuitive GUI browser? Please visit the [mpqa-gui repository](https://github.com/jyuhuan/mpqa-gui).
