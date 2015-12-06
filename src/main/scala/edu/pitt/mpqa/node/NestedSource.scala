package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option.Uncertainty
import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class NestedSource(var spanOfImmediateSource: Span,
                   var agentIDs: Seq[String],
                   var uncertainty: Uncertainty) { self =>
  //region Java Getters and Setters
  def getSpanOfImmediateSource: Span = spanOfImmediateSource
  def getAgentIDs: java.util.List[String] = agentIDs.asJava
  def getUncertainty: Uncertainty = uncertainty

  def setSpanOfImmediateSource(span: Span): Unit = self.spanOfImmediateSource = span
  def setAgentIDs(IDs: java.util.List[String]): Unit = self.agentIDs = IDs.asScala
  def setUncertainty(newValue: Uncertainty): Unit = self.uncertainty = newValue
  //endregion

}
