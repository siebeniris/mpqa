package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class ExpressiveSubjectivity(var span: Span,
                             var id: String,
                             var nestedSource: NestedSource,
                             var targetFrame: TargetFrame,
                             var polarity: Polarity,
                             var intensity: Intensity,
                             var insubstantiality: Insubstantiality,
                             var parent: Sentence) extends SubjObj with HasTargetFrame { self =>

  def sentence: Sentence = parent

  //region Java Getters and Setters
  def getId: String = id
  def getTargetFrame: TargetFrame = targetFrame
  def getPolarity: Polarity = polarity
  def getIntensity: Intensity = intensity
  override def getSentence: Sentence = sentence

  def setSpan(span: Span) = self.span = span
  def setId(newId: String): Unit = self.id = newId
  def setNestedSource(nestedSource: NestedSource): Unit = self.nestedSource = nestedSource
  def setTargetFrame(targetFrame: TargetFrame) = self.targetFrame = targetFrame
  def setPolarity(newValue: Polarity): Unit = self.polarity = newValue
  def setIntensity(newValue: Intensity): Unit = self.intensity = newValue
  def setInsubstantiality(insubstantiality: Insubstantiality): Unit = self.insubstantiality = insubstantiality
  def setParent(sentence: Sentence): Unit = self.parent = sentence

  //endregion

}
