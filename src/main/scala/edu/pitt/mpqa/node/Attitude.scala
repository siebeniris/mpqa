package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.{AttitudeType, Intensity, Polarity}

import scala.collection.JavaConverters._

/**
 * Represents an attitude of a private state.
 *
 * An `Attitude` is used only by a [[DirectSubjective DirectSubjective]]
 * to represent the private state it contains.
 *
 * In MPQA 2, a variety of types of attitudes are annotated with span-based
 * targets. MPQA 3 makes changes only on those attitudes whose type is sentiment in two ways:
 *
 *  - Marks the eTargets in each span-based targets, and
 *  - Finds additional eTargets.
 *
 * @param parent The [[DirectSubjective DirectSubjective]]
 *               that holds the private state this attitude represents.
 * @param id The ID of this attitude annotation.
 * @param nestedSource The nested source of this attitude.
 * @param span The text span of this attitude annotation.
 * @param attitudeType The type of this attitude.
 *                     See [[edu.pitt.mpqa.option.AttitudeType AttitudeType]] for possible values.
 * @param intensity TODO: What is this?
 *                  See [[edu.pitt.mpqa.option.Intensity Intensity]] for possible values.
 * @param polarity TODO: What is this?
 *                 See [[edu.pitt.mpqa.option.Polarity Polarity]] for possible values.
 * @param targetFrame The target frame of this attitude.
 *
 */
class Attitude(val parent: SubjObj,
               val id: String,
               val nestedSource: Seq[String],
               val span: Span,
               val attitudeType: AttitudeType,
               val intensity: Intensity,
               val polarity: Polarity,
               var targetFrame: TargetFrame) extends HasTargetFrame {

  /**
   * The actual text of the span of this attitude annotation.
   * Newline characters are replaced by spaces.
   */
  def spanStr: String = span.str(parent.sentence.document.id).replace('\n', ' ')

  /**
   * The sentence in which this attitude is found.
   */
  def sentence = parent.sentence

  //region Java Compatibility Methods
  /**
   * Gets the [[DirectSubjective DirectSubjective]]
   * that holds the private state this attitude represents.
   */
  def getParent = parent

  /**
   * Gets the ID of this attitude annotation.
   */
  def getId = id

  /**
   * Gets the nested source of this attitude.
   */
  def getNestedSource: java.util.List[String] = nestedSource.asJava

  /**
   * Gets the text span of this attitude annotation.
   */
  def getSpan = span

  /**
   * Gets the type of this attitude.
   * See [[edu.pitt.mpqa.option.AttitudeType AttitudeType]] for possible values.
   */
  def getAttitudeType = attitudeType

  /**
   * TODO: What is this?
   * See [[edu.pitt.mpqa.option.Intensity Intensity]] for possible values.
   */
  def getIntensity = intensity

  /**
   * TODO: What is this?
   * See [[edu.pitt.mpqa.option.Polarity Polarity]] for possible values.
   */
  def getPolarity = polarity

  /**
   * Gets the target frame of this attitude.
   */
  def getTargetFrame = targetFrame

  /**
   * Gets the actual text of the span of this attitude annotation.
   * Newline characters are replaced by spaces.
   */
  def getSpanStr = spanStr

  /**
   * Gets the sentence in which this attitude is found.
   */
  def getSentence = sentence
  //endregion

  override def toString = spanStr

}

