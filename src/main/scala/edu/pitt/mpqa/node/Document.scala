package edu.pitt.mpqa.node

import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Document(var name: String, var text: String, var agents: Seq[Agent], var sentences: Seq[Sentence]) { self =>
  //region Java Getters and Setters
  def getName: String = name
  def getText: String = text
  def getAgents: java.util.List[Agent] = agents.asJava
  def getSentences: java.util.List[Sentence] = sentences.asJava

  def setName(name: String): Unit = self.name = name
  def setText(text: String): Unit = self.text = text
  def setAgents(agents: java.util.List[Agent]): Unit = self.agents = agents.asScala
  def setSentences(sentences: java.util.List[Sentence]): Unit = self.sentences = sentences.asScala
  //endregion

}
