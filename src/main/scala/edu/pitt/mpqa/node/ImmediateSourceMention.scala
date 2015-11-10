package edu.pitt.mpqa.node

import edu.pitt.mpqa.option.Uncertainty
import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class ImmediateSourceMention(val nestedSource: Seq[String], val span: Span, val uncertainty: Uncertainty) {
  //region Java Compatibility Methods
  def getNestedSource: java.util.List[String] = nestedSource.asJava
  def getSpan = span
  def getUncertainty = uncertainty
  //endregion
}
