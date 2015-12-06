package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Sentence(var span: Span,
               var subjObjs: Seq[SubjObj],
               var parent: Document) { self =>
  //region Java Getters and Setters
  def getSpan: Span = span
  def getSubjObjs: java.util.List[SubjObj] = subjObjs.asJava
  def getParent: Document = parent

  def setSpan(span: Span): Unit = self.span = span
  def setSubjObjs(subjObjs: java.util.List[SubjObj]): Unit = self.subjObjs = subjObjs.asScala
  def setParent(document: Document): Unit = self.parent = document
  //endregion

}
