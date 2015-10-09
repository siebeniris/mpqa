package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.{ETargetType, NegatedOption, ReferredInSpanOption}

/**
 * Represents an eTarget.
 *
 * An eTarget is the head of an event or entity that is the target of some
 * subjective or objective annotation.
 *
 * @param parent The subjective or objective annotation that this eTarget belongs to.
 * @param id The ID of this eTarget.
 * @param span The text span of this eTarget.
 * @param eTargetType Whether this eTarget is the head of an event or of an entity.
 *                    See [[edu.pitt.mpqa.option.ETargetType ETargetType]] for possible values.
 * @param isNegated TODO: What is this?
 *                  See [[edu.pitt.mpqa.option.ReferredInSpanOption ReferredInSpanOption]] for possible values.
 * @param isReferredInSpan TODO: What is this?
 *                         See [[edu.pitt.mpqa.option.ReferredInSpanOption ReferredInSpanOption]] for possible values.
 */
class ETarget(val parent: HasETarget,
              val id: String,
              val span: Span,
              val eTargetType: ETargetType,
              val isNegated: NegatedOption,
              val isReferredInSpan: ReferredInSpanOption) extends Annotation {

  /**
   * The actual text of the span of this eTarget annotation.
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
   * The sentence to which this eTarget belongs.
   */
  def sentence = parent match {
    case tf: TargetFrame ⇒ tf.parent match {
      case p: ExpressiveSubjectivity ⇒ p.sentence
      case p: Attitude ⇒ p.parent.sentence
    }
    case s: STarget ⇒ s.sentence
  }

  override def toString = spanStr

  //region Java Compatibility Methods
  /**
   * Gets the subjective or objective annotation that this eTarget belongs to.
   */
  def getParent = parent

  /**
   * Gets the ID of this eTarget.
   */
  def getId = id

  /**
   * Gets the text span of this eTarget.
   */
  def getSpan = span

  /**
   * Gets whether this eTarget is the head of an event or of an entity.
   * See [[edu.pitt.mpqa.option.ETargetType ETargetType]] for possible values.
   */
  def getETargetType = eTargetType

  /**
   * TODO: what is this?
   * See [[edu.pitt.mpqa.option.NegatedOption NegatedOption]] for possible values.
   * @return
   */
  def getIsNegated = isNegated

  /**
   * TODO: what is this?
   * See [[edu.pitt.mpqa.option.ReferredInSpanOption ReferredInSpanOption]] for possible values.
   */
  def getIsReferredInSpan = isReferredInSpan

  /**
   * Gets the actual text of the span of this eTarget annotation.
   * Newline characters are replaced by spaces.
   */
  def getSpanStr = spanStr

  /**
   * Gets the sentence to which this eTarget belongs.
   */
  def getSentence = sentence
  //endregion


  //region Conforming to Annotation
  override def children: Seq[Annotation] = Nil
  //endregion
}

