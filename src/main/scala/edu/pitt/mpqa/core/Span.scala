package edu.pitt.mpqa.core

import edu.pitt.mpqa.node.Document

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
case class Span(start: Int, end: Int) {
  /**
    * Tests if this span subsumes a given span.
 *
    * @see [[Span#properlySubsumes()]]
    * @param that The span to compare to.
    * @return `true` if this span indeed subsumes the given span. `false` otherwise.
    */
  def subsumes(that: Span) = this.start <= that.start && this.end >= that.end

  /**
    * Tests if this span properly subsumes a given span.
    * Span(2,5) does not properly subsume Span(2,5), Span(2,4) or Span(3,5), while Span(a,b)
    * does properly subsume Span(3,4).
    *
    * @param that The span to compare to.
    * @return `true` if this span indeed subsumes the given span. `false` otherwise.
    */
  def properlySubsumes(that: Span) = this.start < that.start && this.end > that.end

  /**
    * Length of this span.
    */
  def length = end - start

  /**
    * Obtains the text that this span actually points to in the given document.
    * If the document is `"Hello world!"`, and this span is Span(3, 7), this method returns
    * `"lo w"`.
 *
    * @param document The document in which the text is stored.
    * @return The text that this span actually represents.
    */
  def str(source: String): String = source.substring(start, end)

  def getStr(source: String): String = str(source)
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