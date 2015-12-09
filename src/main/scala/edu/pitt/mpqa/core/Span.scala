package edu.pitt.mpqa.core

import edu.pitt.mpqa.node.Document

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
case class Span(start: Int, end: Int) {
  def subsumes(that: Span) = this.start > that.start && this.end < that.end
  def length = end - start

  def text(document: Document): String = document.text.substring(start, end)

  def getText(document: Document): String = text(document)
  def getLength = length
}
