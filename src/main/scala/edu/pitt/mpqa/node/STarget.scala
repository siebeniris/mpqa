package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.Uncertainty

import scala.collection.JavaConverters._

/**
 * Represents a target span (i.e., a span-based target).
 *
 * A '''target span''' is a span of text that is annotated as the target of
 * subjective or objective annotations in MPQA 2.
 *
 * @param parent The target frame or objective annotation that this target span belongs to.
 * @param id The ID of this target span.
 * @param span The text span of this target span.
 * @param uncertainty TODO: What is this?
 *                    See [[edu.pitt.mpqa.option.Uncertainty Uncertainty]] for possible values.
 */
class STarget(val parent: HasSTarget,
              val id: String,
              val span: Span,
              var eTargets: Seq[ETarget],
              val uncertainty: Uncertainty) extends Annotation with HasETarget {

  /**
   * The actual text of the span of this sTarget annotation.
   * Newline characters are replaced by spaces.
   */
  def spanStr: String = {
    if (span != null) {
      val docName = sentence.document.id
      span.str(docName).replace('\n', ' ')
    }
    else "(No span)"
  }

  /**
   * The sentence to which this sTarget belongs.
   */
  def sentence = parent match {
    case p: TargetFrame ⇒ p.parent match {
      case pp: Attitude ⇒ pp.parent.sentence
    }
    case p: ObjectiveSpeechEvent ⇒ p.sentence
  }

  override def toString = spanStr

  //region Java Compatibility Methods
  /**
   * Gets The target frame or objective annotation that this target span belongs to.
   */
  def getParent = parent

  /**
   * Gets the ID of this target span.
   */
  def getId = id

  /**
   * Gets the text span of this target span.
   * @return
   */
  def getSpan = span

  def getETargets: java.util.List[ETarget] = eTargets.asJava

  /**
   * TODO: What is this?
   * See [[edu.pitt.mpqa.option.Uncertainty Uncertainty]] for possible values.
   */
  def getUncertainty = uncertainty

  /**
   * Gets the actual text of the span of this sTarget annotation.
   * Newline characters are replaced by spaces.
   */
  def getSpanStr = spanStr

  /**
   * Gets the sentence to which this sTarget belongs.
   */
  def getSentence = sentence
  //endregion


  //region Conforming to Annotation
  override def children: Seq[Annotation] = eTargets
  //endregion
}


