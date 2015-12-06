package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option.Insubstantiality

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class ObjectiveSpeech(var span: Span,
                      var nestedSource: NestedSource,
                      var targetFrame: TargetFrame,
                      var insubstantiality: Insubstantiality,
                      var parent: Sentence) extends SubjObj with HasTargetFrame { self =>
  //region Java Getters and Setters
  def getSpan: Span = span
  def getNestedSource: NestedSource = nestedSource
  def getTargetFrame: TargetFrame = targetFrame
  def getInsubstantiality: Insubstantiality = insubstantiality
  def getParent: Sentence = parent

  def setSpan(span: Span): Unit = self.span = span
  def setNestedSource(nestedSource: NestedSource): Unit = self.nestedSource = nestedSource
  def setTargetFrame(targetFrame: TargetFrame): Unit = self.targetFrame = targetFrame
  def setInsubstantiality(insubstantiality: Insubstantiality): Unit = self.insubstantiality = insubstantiality
  def setParent(sentence: Sentence): Unit = self.parent = sentence
  //endregion

}
