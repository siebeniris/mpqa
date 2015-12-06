package edu.pitt.mpqa.core

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
case class Span(start: Int, end: Int) {
  def subsumes(that: Span) = this.start > that.start && this.end < that.end
  def length = end - start

  def getLength = length
}
