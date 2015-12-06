package edu.pitt.mpqa.node
import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class ETarget(var span: Span,
              var id: String,
              var typ: ETargetType,
              var isNegated: NegatedOption,
              var isReferredInSpan: ReferredInSpanOption,
              var parent: HasETarget) { self =>
  //region Java Getters and Setters
  def getSpan: Span = span
  def getId: String = id
  def getType: ETargetType = typ
  def getIsNegated: NegatedOption = isNegated
  def getIsReferredInSpan: ReferredInSpanOption = isReferredInSpan
  def getParent: HasETarget = parent

  def setSpan(span: Span): Unit = self.span = span
  def setId(id: String): Unit = self.id = id
  def setType(newType: ETargetType): Unit = self.typ = newType
  def setIsNegated(newValue: NegatedOption): Unit = self.isNegated = newValue
  def setIsReferredInSpan(newValue: ReferredInSpanOption): Unit = self.isReferredInSpan = newValue
  def setParent(newParent: HasETarget): Unit = self.parent = newParent
  //endregion

}
