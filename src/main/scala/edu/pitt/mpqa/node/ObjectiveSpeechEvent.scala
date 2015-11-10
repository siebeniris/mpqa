package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.Insubstantiality
import scala.collection.JavaConverters._

/**
 * Represents an objective speech event.
 *
 * An '''objective speech event''' (OSE) is a speech event (written or spoken)
 * not expressing any private states.
 *
 * @param sentence The sentence that this OSE belongs to.
 * @param nestedSource The nested source of this OSE.
 * @param span The text span of this OSE annotation.
 * @param sTarget The span target of this OSE. Notice that this is not a
 *               [[TargetFrame TargetFrame]]
 *               because the target annotation in MPQA for OSEs only consists of
 *               the span-based targets and no target frame.
 * @param insubstantiality (TODO: What exactly are the criteria for a DS to be insubstantial? )
 *                         See [[edu.pitt.mpqa.option.Insubstantiality Insubstantiality]] for possible values.
 */
class ObjectiveSpeechEvent(val sentence: Sentence,
                           val nestedSource: Seq[String],
                           val immediateSourceMention: ImmediateSourceMention,
                           val span: Span,
                           var sTarget: STarget,
                           val insubstantiality: Insubstantiality) extends SubjObj with HasSTarget {


  /**
   * The actual text of the span of this OSE annotation.
   * Newline characters are replaced by spaces.
   */
  def spanStr: String =
    if (span.length > 1) span.str(sentence.document.id).replace('\n', ' ') else sentence.spanStr

  override def toString = spanStr

  /**
   * The sTargets in this OSE.
   * An OSE in MPQA has at most one target. Thus this is a list of at most one item.
   * This method is necessary to conform to the trait
   * [[HasSTarget HasSTarget]].
   */
  def sTargets = if (sTarget != null) Array(sTarget) else Array[STarget]()

  //region Java Compatibility Methods
  /**
   * Gets the sentence that this OSE belongs to.
   */
  def getSentence = sentence

  /**
   * Gets the nested source of this OSE.
   */
  def getNestedSource: java.util.List[String] = nestedSource.asJava

  /**
   * Gets the text span of this OSE annotation.
   */
  def getSpan = span

  /**
   * The span target of this OSE. Notice that this is not a
   * [[TargetFrame TargetFrame]]
   * because the target annotation in MPQA for OSEs only consists of
   * the span-based targets and no target frame.
   */
  def getSTarget = sTarget

  /**
   * (TODO: What exactly are the criteria for a DS to be insubstantial? )
   * See [[edu.pitt.mpqa.option.Insubstantiality Insubstantiality]] for possible values.
   */
  def getInsubstantiality = insubstantiality

  /**
   * Gets the actual text of the span of this OSE annotation.
   * Newline characters are replaced by spaces.
   */
  def getSpanStr = spanStr

  /**
   * Gets the sTargets in this OSE.
   * An OSE in MPQA has at most one target. Thus this is a list of at most one item.
   * This method is necessary to conform to the trait
   * [[HasSTarget HasSTarget]].
   */
  def getSTargets = sTargets
  //endregion


}

