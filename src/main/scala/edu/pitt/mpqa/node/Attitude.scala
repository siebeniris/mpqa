package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.{AttitudeType, Intensity, Polarity}

import scala.collection.JavaConverters._

/**
 * Represents an attitude of a private state.
 *
 * An `Attitude` is used by a [[DirectSubjective DirectSubjective]]
 * to represent the private state it contains.
 *
 * In MPQA 2, a various types (TODO: which exactly) of attitudes are annotated with span-based
 * targets. MPQA 3 makes changes only on those attitudes whose type is sentiment in two ways:
 *
 *  - Marks the eTargets in each span-based targets
 *  - Finds additional targets, marks the eTargets of these new targets.
 *
 * @param parent The [[DirectSubjective DirectSubjective]]
 *               that holds the private state that this attitude represents.
 * @param id The ID of this attitude annotation.
 * @param nestedSource The nested source of this attitude.
 * @param span The text span of this attitude annotation.
 * @param attitudeType The type of this attitude.
 * @param intensity TODO:
 * @param polarity TODO:
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
   * Gets the actual text of the span of this attitude annotation.
   * Newline characters are replaced by spaces.
   */
  def spanStr: String = span.str(parent.sentence.document.id).replace('\n', ' ')

  def sentence = parent.sentence

  //region Java Compatibility Methods
  def getParent = parent
  def getId = id
  def getNestedSource: java.util.List[String] = nestedSource.asJava
  def getSpan = span
  def getSpanStr = spanStr
  def getAttitudeType = attitudeType
  def getIntensity = intensity
  def getPolarity = polarity
  //endregion

  override def toString = spanStr

}

