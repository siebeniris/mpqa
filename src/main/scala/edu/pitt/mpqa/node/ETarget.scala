package edu.pitt.mpqa.node

import edu.pitt.mpqa.node.Span
import edu.pitt.mpqa.option.{ReferredInSpanOption, NegatedOption, ETargetType}

/**
 * Represents an eTarget.
 *
 * An eTarget is the head of an event or entity that is the target of some
 * subjective or objective annotation.
 *
 * @param parent The subjective or objective annotation that this eTarget belongs to.
 * @param id The ID of this eTarget.
 * @param span The text span of this eTarget.
 * @param eventOrEntity Whether this eTarget is the head of an event or of an entity.
 * @param isNegated TODO: What is this?
 * @param isReferredInSpan TODO: What is this?
 */
class ETarget(val parent: TargetFrame,
              val id: String,
              val span: Span,
              val eventOrEntity: ETargetType,
              val isNegated: NegatedOption,
              val isReferredInSpan: ReferredInSpanOption) extends Annotation {

  /**
   * Gets the actual text of the span of this eTarget annotation.
   * Newline characters are replaced by spaces.
   */
  def spanStr: String = {
    if (span != null) {
      val docName = sentence.document.id
      span.str(docName).replace('\n', ' ')
    }
    else "(No span)"
  }

  def sentence = parent.parent match {
    case p: ExpressiveSubjectivity ⇒ p.sentence
    case p: Attitude ⇒ p.parent.sentence
  }

  override def toString = spanStr

  //region Java Compatibility Methods
  def getParent = parent
  def getId = id
  def getSpan = span
  def getSpanStr = spanStr
  //endregion


  //region Conforming to Annotation
  override def children: Seq[Annotation] = Nil
  //endregion
}

