package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class Agent(var parent: Document,
            var id: String,
            var span: Span) { self =>

  //region Java Getters and Setters
  def getParent: Document = parent
  def getId: String = id
  def getSpan: Span = span

  def setParent(document: Document): Unit = self.parent = document
  def setId(id: String): Unit = self.id = id
  def setSpan(span: Span): Unit = self.span = span
  //endregion
}
