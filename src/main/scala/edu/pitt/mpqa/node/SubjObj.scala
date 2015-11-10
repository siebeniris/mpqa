package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.Insubstantiality

import scala.collection.JavaConverters._

/**
 * Represents a subjective or objective annotation.
 *
 * The following is a list of possible `SubjObj`s:
 *
 *  - direct subjectives ([[DirectSubjective DirectSubjective]]),
 *  - expressive subjectivities ([[ExpressiveSubjectivity ExpressiveSubjectivity]]), and
 *  - objective speech events ([[ObjectiveSpeechEvent ObjectiveSpeechEvent]])
 *
 */
trait SubjObj extends Annotation {
  def sentence: Sentence
  def span: Span
  def nestedSource: Seq[String]
  def insubstantiality: Insubstantiality

  def immediateSourceMention: ImmediateSourceMention

  //region Conforming to TreeNode[Annotation]
  override def parent: Annotation = sentence
  //endregion

  def getImmediateSourceMention = immediateSourceMention
}
