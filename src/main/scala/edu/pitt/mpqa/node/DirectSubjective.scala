package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option._

import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class DirectSubjective(var span: Span,
                       var id: String,
                       var nestedSource: NestedSource,
                       var attitudes: Seq[Attitude],
                       var expressionIntensity: ExpressionIntensity,
                       var intensity: Intensity,
                       var insubstantiality: Insubstantiality,
                       var parent: Sentence) extends SubjObj { self =>

  //region Java Getters and Setters
  def getSpan: Span = span
  def getId: String = id
  def getNestedSource: NestedSource = nestedSource
  def getAttitudes: java.util.List[Attitude] = attitudes.asJava
  def getExpressionIntensity: ExpressionIntensity = expressionIntensity
  def getIntensity: Intensity = intensity
  def getInsubstantiality: Insubstantiality = insubstantiality
  def getParent: Sentence = parent

  def setSpan(span: Span): Unit = self.span = span
  def setId(newId: String): Unit = self.id = newId
  def setNestedSource(nestedSource: NestedSource): Unit = self.nestedSource = nestedSource
  def setAttitudes(attitudes: java.util.List[Attitude]): Unit = attitudes.asScala
  def setExpressionIntensity(expressionIntensity: ExpressionIntensity): Unit = self.expressionIntensity = expressionIntensity
  def setIntensity(intensity: Intensity): Unit = self.intensity = intensity
  def setInsubstantiality(insubstantiality: Insubstantiality): Unit = self.insubstantiality = insubstantiality
  def setParent(sentence: Sentence): Unit = self.parent = sentence
  //endregion

}
