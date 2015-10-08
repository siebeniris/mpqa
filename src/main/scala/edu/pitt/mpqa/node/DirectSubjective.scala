package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.{ExpressionIntensity, Insubstantiality, Intensity}

import scala.collection.JavaConverters._

/**
 * Represents a direct subjective annotation.
 *
 * A '''direct subjectivity''' (DS) is for a direct expression of private states, or
 * a subjective speech event. A DS object does not have targets directly as one of its field,
 * the targets are found in the `attitudes` field.
 *
 * See [[Attitude Attitude]] for a description of changes from MPQA 2 to 3.
 *
 * @param sentence The sentence that this DS belongs to.
 * @param span The text span of this DS annotation.
 * @param nestedSource The nested source of this DS.
 * @param attitudes A collection of attitude annotations in this DS.
 * @param expressionIntensity The intensity of the direct private state expression or
 *                            the speech event itself in this DS annotation.
 * @param intensity The overall intensity of the private state being expressed, considering this
 *                  DS and everything inside its scope.
 * @param insubstantiality (TODO: What exactly are the criteria for a DS to be insubstantial? )
 *
 */
class DirectSubjective(val sentence: Sentence,
                       val span: Span,
                       val nestedSource: Seq[String],
                       var attitudes: Seq[Attitude],
                       val expressionIntensity: ExpressionIntensity,
                       val intensity: Intensity,
                       val insubstantiality: Insubstantiality) extends SubjObj {

  /**
   * Gets the actual text of the span of this DS annotation.
   * Newline characters are replaced by spaces.
   */
  def spanStr: String = span.str(sentence.document.id).replace('\n', ' ')

  override def toString = spanStr


  //region Java Compatibility Methods
  def getAttitudes: java.util.List[Attitude] = attitudes.asJava
  def getSpanStr = spanStr
  def getIntensity = intensity
  def getExpressionIntensity = expressionIntensity
  //endregion

  //region Conforming to Annotation
  override def children: Seq[Annotation] = attitudes
  //endregion

}

