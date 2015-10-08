package edu.pitt.mpqa.node

import scala.collection.JavaConverters._

/**
 * Represents a sentence in MPQA.
 *
 * A sentence consists of subjectivity and objectivity annotations.
 *
 * @param document The document that the sentence belongs to.
 * @param span The text span of the sentence.
 * @param subjObjs The subjectivity and objectivity annotations in this sentence.
 */
class Sentence(val document: Document,
               val span: Span,
               var subjObjs: Seq[SubjObj]) extends Annotation {

  /**
   * Gets the text string of the sentence.
   * Newline characters are replaced with spaces.
   */
  def spanStr: String = span.str(document.id).replace('\n', ' ')

  override def toString = spanStr

  //region Java Compatibility Methods
  def getDocument = document
  def getSpan = span
  def getSpanStr = spanStr
  def getSubjObjs: java.util.List[SubjObj] = subjObjs.asJava
  //endregion

  //region conforming to Annotation
  override def parent: Annotation = null
  override def children: Seq[Annotation] = subjObjs
  //endregion
}

