package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.{Insubstantiality, Intensity, Polarity}
import scala.collection.JavaConverters._

/**
 * Represents an expressive subjectivity element.
 *
 * An '''expressive subjective element''' (ESE) pinpoints specific expressions
 * used to express subjectivity.
 *
 * In MPQA 2, ESEs are not annotated with any kind of targets.
 * It was not until MPQA 3 that the targets were added.
 * Thus, the targets in an ESE are all eTargets.
 *
 * @param sentence The sentence that this ESE belong to.
 * @param nestedSource The nested source of this ESE.
 * @param span The text span of this ESE annotation.
 * @param polarity The polarity of this ESE.
 *                 See [[edu.pitt.mpqa.option.Polarity Polarity]] for possible values.
 * @param intensity The intensity of this ESE.
 *                  See [[edu.pitt.mpqa.option.Intensity Intensity]] for possible values.
 * @param insubstantiality TODO: what is this
 *                         See [[edu.pitt.mpqa.option.Insubstantiality Insubstantiality]] for possible values.
 * @param targetFrame The target frame of this ESE.
 */
class ExpressiveSubjectivity(val sentence: Sentence,
                             val nestedSource: Seq[String],
                             val span: Span,
                             val polarity: Polarity,
                             val intensity: Intensity,
                             val insubstantiality: Insubstantiality,
                             var targetFrame: TargetFrame) extends SubjObj with HasTargetFrame {

  /**
   * The actual text of the span of this ESE annotation.
   * Newline characters are replaced by spaces.
   */
  def spanStr: String = span.str(sentence.document.id).replace('\n', ' ')
  override def toString = spanStr

  //region Java Compatibility Methods
  /**
   * Gets the sentence that this ESE belong to.
   */
  def getSentence = sentence

  /**
   * Gets the nested source of this ESE.
   */
  def getNestedSource: java.util.List[String] = nestedSource.asJava

  /**
   * Gets the text span of this ESE annotation.
   */
  def getSpan = span

  /**
   * Gets the polarity of this ESE.
   * See [[edu.pitt.mpqa.option.Polarity Polarity]] for possible values.
   */
  def getPolarity = polarity

  /**
   * Gets the intensity of this ESE.
   * See [[edu.pitt.mpqa.option.Intensity Intensity]] for possible values.
   */
  def getIntensity = intensity

  /**
   * TODO: what is this
   * See [[edu.pitt.mpqa.option.Insubstantiality Insubstantiality]] for possible values.
   */
  def getInsubstantiality = insubstantiality

  /**
   * Gets the target frame of this ESE.
   */
  def getTargetFrame = targetFrame

  /**
   * Gets the actual text of the span of this ESE annotation.
   * Newline characters are replaced by spaces.
   */
  def getSpanStr = spanStr
  //endregion


}

