package edu.pitt.mpqa.node

import edu.pitt.mpqa.Mpqa

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 10/8/15.
 */
/**
 * Represents a text span.
 *
 * @param startPos The position of the starting character.
 * @param endPos The position of the ending character.
 */
case class Span(startPos: Int, endPos: Int) {
  def subsumes(that: Span) = this.startPos > that.startPos && this.endPos < that.endPos
  def length = endPos - startPos
}




object Span {

  def empty = Span(0, 0)

  implicit class EasySpanConstruct(val x: Int) extends AnyVal {
    def ~(y: Int) = Span(x, y)
  }

  implicit class SpanIsViewable(val s: Span) extends AnyVal {
    /**
     * Get the actual string of the span, given the document to which it belongs.
     * @param docName Document name in the MPQA corpus. Looks like "20011024/21.53.09-12345"
     * @return The actual string of the span.
     */
    def str(docName: String) = Mpqa.view(docName, s)
  }

}