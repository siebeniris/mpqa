package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class ExpressiveSubjectivity(var span: Span,
                             var nestedSource: NestedSource,
                             var targetFrame: TargetFrame,
                             var polarity: Polarity,
                             var intensity: Intensity,
                             var insubstantiality: Insubstantiality,
                             var parent: Sentence) extends SubjObj with HasTargetFrame { self =>
  //region Java Getters and Setters
  def getSpan: Span = span
  def getNestedSource: NestedSource = nestedSource
  def getTargetFrame: TargetFrame = targetFrame
  def getPolarity: Polarity = polarity
  def getIntensity: Intensity = intensity
  def getInsubstantiality: Insubstantiality = insubstantiality
  def getParent: Sentence = parent

  def setSpan(span: Span) = self.span = span
  def setNestedSource(nestedSource: NestedSource): Unit = self.nestedSource = nestedSource
  def setTargetFrame(targetFrame: TargetFrame) = self.targetFrame = targetFrame
  def setPolarity(newValue: Polarity): Unit = self.polarity = newValue
  def setIntensity(newValue: Intensity): Unit = self.intensity = intensity
  def setInsubstantiality(insubstantiality: Insubstantiality): Unit = self.insubstantiality = insubstantiality
  def setParent(sentence: Sentence): Unit = self.parent = sentence

  //endregion

}
