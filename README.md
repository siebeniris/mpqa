# The Scala/Java API for MPQA 3.0
The **Scala/Java API for MPQA 3.0** is the simplest way to incorporate the [MPQA 3.0](http://mpqa.cs.pitt.edu/corpora/mpqa_corpus/) corpus into your project. It is designed to be simple and intuitive. The API is most friendly to Scala users, since it is written in Scala. But it also exposes Java-friendly methods with which a Java user can feel comfortable. 

## Installation
### For Scala Users
Simply add the following two lines to the `build.sbt` file of your project. 

```sbt
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "me.yuhuan" %% "mpqa" % "0.0.0-SNAPSHOT"
```

### For Java Users
Simply add the following lines to your Maven 

    <dependency>
      <groupId> me.yuhuan </groupId>
      <artifactId> mpqa_2.11</artifactId>
      <version>0.0.0-SNAPSHOT</version>
    </dependency>

## Documentation
For ScalaDoc (the machine-generated documentation for classes and methods), please [click here]().

For the human-written explanation of structure design and tutorials on how to use the API, please [click here](https://github.com/jyuhuan/mpqa/wiki).

## GUI Browser
Looking for the intuitive GUI browser? Please visit the [mpqa-gui repository](https://github.com/jyuhuan/mpqa-gui).
