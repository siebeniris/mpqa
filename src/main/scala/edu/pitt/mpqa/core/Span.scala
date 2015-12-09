package edu.pitt.mpqa.core

import edu.pitt.mpqa.node.Document

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
case class Span(start: Int, end: Int) {
  def subsumes(that: Span) = this.start <= that.start && this.end >= that.end
  def properlySubsumes(that: Span) = this.start < that.start && this.end > that.end
  def length = end - start

  def text(document: Document): String = document.text.substring(start, end)

  def getText(document: Document): String = text(document)
  def getLength = length
}

private object SpanTest extends App {
  val s1 = Span(2, 100)
  val s2 = Span(2, 99)
  val s3 = Span(3, 100)
  val s4 = Span(3, 99)

  val s5 = Span(2, 101)
  val s6 = Span(1, 100)
  val s7 = Span(1, 101)

  assert(s1 subsumes s2)
  assert(s1 subsumes s3)
  assert(s1 subsumes s4)

  assert(!(s1 subsumes s5))
  assert(!(s1 subsumes s6))
  assert(!(s1 subsumes s7))

  assert(!(s1 properlySubsumes s2))
  assert(!(s1 properlySubsumes s3))
  assert(s1 properlySubsumes s4)


}