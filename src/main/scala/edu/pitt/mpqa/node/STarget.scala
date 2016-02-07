package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option.Uncertainty

import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class STarget(var span: Span,
              var id: String,
              var uncertainty: Uncertainty,
              var eTargets: Seq[ETarget],
              var parent: TargetFrame) extends HasETarget { self =>

  def spanStr: String = {
    val source = parent.parent match {
      case att: Attitude => att.parent.parent.parent.text
      case es: ExpressiveSubjectivity => es.parent.parent.text
      case os: ObjectiveSpeech => os.parent.parent.text
    }
    span.str(source)
  }

  //region Java Getters and Setters
  def getSpan: Span = span
  def getId: String = id
  def getUncertainty: Uncertainty = uncertainty
  def getETargets: java.util.List[ETarget] = eTargets.asJava
  def getParent: TargetFrame = parent

  def setSpan(span: Span): Unit = self.span = span
  def setId(id: String): Unit = self.id = id
  def setUncertainty(newValue: Uncertainty): Unit = self.uncertainty = newValue
  def setETargets(eTargets: java.util.List[ETarget]): Unit = self.eTargets = eTargets.asScala
  def setParent(targetFrame: TargetFrame): Unit = self.parent = targetFrame
  //endregion

}
