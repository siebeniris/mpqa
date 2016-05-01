package edu.pitt.mpqa.node
import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option._
import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class ETarget(var span: Span,
              var id: String,
              var typ: ETargetType,
              var isNegated: NegatedOption,
              var isReferredInSpan: ReferredInSpanOption,
              var parents: Seq[HasETarget]) { self =>

  def spanStr: String = {
    val source: String = parents.head match {
      case s: STarget => s.parent.parent match {
        case att: Attitude => att.parent.parent.parent.text
        case es: ExpressiveSubjectivity => es.parent.parent.text
        case os: ObjectiveSpeech => os.parent.parent.text
      }
      case tf: TargetFrame => tf.parent match {
        case att: Attitude => att.parent.parent.parent.text
        case es: ExpressiveSubjectivity => es.parent.parent.text
        case os: ObjectiveSpeech => os.parent.parent.text
      }
    }
    span.str(source)
  }

  def sentence: Sentence = self.parents.head.sentence

  //region Java Getters and Setters
  def getSpan: Span = span
  def getId: String = id
  def getType: ETargetType = typ
  def getIsNegated: NegatedOption = isNegated
  def getIsReferredInSpan: ReferredInSpanOption = isReferredInSpan
  def getParents: java.util.List[HasETarget] = parents.asJava
  def getSentence: Sentence = sentence

  def setSpan(span: Span): Unit = self.span = span
  def setId(id: String): Unit = self.id = id
  def setType(newType: ETargetType): Unit = self.typ = newType
  def setIsNegated(newValue: NegatedOption): Unit = self.isNegated = newValue
  def setIsReferredInSpan(newValue: ReferredInSpanOption): Unit = self.isReferredInSpan = newValue
  def setParents(newParents: java.util.List[HasETarget]): Unit = self.parents = newParents.asScala
  //endregion

}
