package edu.pitt.mpqa.node

import scala.collection.JavaConverters._

/**
 * Represents a document in MPQA.
 *
 * A document mainly consists of sentences.
 *
 * @param id The ID of this document.
 *           In MPQA, document IDs have the form "DATE/NAME", where DATE is
 *           an 8-digit number, and NAME is (TODO:).
 * @param agentMentions All agents mentioned in this document.
 * @param sentences Sentences that this document consists of.
 *
 */
class Document(val id: String,
               val agentMentions: Seq[Agent],
               var sentences: Seq[Sentence]) extends Annotation {

  def spanStr = sentences.map(_.spanStr).mkString("\n")

  def briefSpanStr = sentences.head.spanStr.take(6) + " ... " + sentences.last.spanStr.takeRight(6)

  def sentencesInOrder = sentences.sortBy(_.span.startPos)

  override def toString = id

  //region Java Compatibility Methods
  def getId = id
  def getSpanStr = spanStr
  def getAgentMentions: java.util.List[Agent] = agentMentions.asJava
  def getSentences: java.util.List[Sentence] = sentences.asJava
  //endregion

  //region conforming to TreeNode[Annotation]
  override def parent: Annotation = ???
  override def children: Seq[Annotation] = sentencesInOrder
  //endregion
}

