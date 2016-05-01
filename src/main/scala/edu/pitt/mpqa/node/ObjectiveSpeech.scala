package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option.Insubstantiality

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class ObjectiveSpeech(var span: Span,
                      var id: String,
                      var nestedSource: NestedSource,
                      var targetFrame: TargetFrame,
                      var insubstantiality: Insubstantiality,
                      var parent: Sentence) extends SubjObj with HasTargetFrame { self =>

  def sentence: Sentence = parent

  //region Java Getters and Setters
  def getId: String = id
  def getTargetFrame: TargetFrame = targetFrame
  override def getSentence: Sentence = sentence

  def setSpan(span: Span): Unit = self.span = span
  def setId(newId: String): Unit = self.id = newId
  def setNestedSource(nestedSource: NestedSource): Unit = self.nestedSource = nestedSource
  def setTargetFrame(targetFrame: TargetFrame): Unit = self.targetFrame = targetFrame
  def setInsubstantiality(insubstantiality: Insubstantiality): Unit = self.insubstantiality = insubstantiality
  def setParent(sentence: Sentence): Unit = self.parent = sentence
  //endregion

}
