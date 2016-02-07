package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option._


/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Attitude(var span: Span,
               var id: String,
               var nestedSource: NestedSource,
               var targetFrame: TargetFrame,
               var typ: AttitudeType,
               var intensity: Intensity,
               var polarity: Polarity,
               var parent: DirectSubjective) extends HasTargetFrame { self =>

  def spanStr: String = span.str(parent.parent.parent.text)

  //region Java Getters and Setters
  def getSpan: Span = span
  def getId: String = id
  def getNestedSource: NestedSource = nestedSource
  def getTargetFrame: TargetFrame = targetFrame
  def getType: AttitudeType = typ
  def getIntensity: Intensity = intensity
  def getPolarity: Polarity = polarity
  def getParent: DirectSubjective = parent

  def setSpan(span: Span): Unit = self.span = span
  def setId(id: String): Unit = self.id = id
  def setNestedSource(nestedSource: NestedSource): Unit = self.nestedSource = nestedSource
  def setTargetFrame(targetFrame: TargetFrame): Unit = self.targetFrame = targetFrame
  def setType(attitudeType: AttitudeType): Unit = self.typ = attitudeType
  def setIntensity(intensity: Intensity): Unit = self.intensity = intensity
  def setPolarity(polarity: Polarity): Unit = self.polarity = polarity
  def setParent(ds: DirectSubjective): Unit = self.parent = ds
  //endregion
}
