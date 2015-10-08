package edu.pitt.mpqa.node

/**
 * This trait simply means that an annotation is a treenode.
 *
 * Unifies the data in all annotations including
 *
 *   * DS
 *   * ESE
 *   * OSE
 *   * Attitude
 *   * TargetFrame
 *   * sTarget
 *   * eTarget
 *
 */
trait Annotation {
  def parent: Annotation
  def children: Iterable[Annotation]
}

//object Annotation {
//  val nullString = "(null)"
//
//
//
//  implicit def allAnnotationsCanRender(x: Annotation): PropertyTableFormatter = x match {
//    case x: ETarget ⇒ eTargetCanRender(x)
//    case x: STarget ⇒ sTargetCanRender(x)
//    case x: TargetFrame ⇒ targetFrameCanRender(x)
//    case x: ObjectiveSpeechEvent ⇒ objectiveSpeechEventCanRender(x)
//    case x: ExpressiveSubjectivity ⇒ expressiveSubjectivityCanRender(x)
//    case x: DirectSubjective ⇒ directSubjectiveCanRender(x)
//    case x: Attitude ⇒ attitudeCanRender(x)
//    case x: Sentence ⇒ sentenceCanRender(x)
//    case x: Document ⇒ documentCanRender(x)
//  }
//
//
//  def eTargetCanRender(x: ETarget): PropertyTableFormatter = new PropertyTableFormatter {
//
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.spanStr,
//      "Type" → (if (x.eventOrEntity != null) x.eventOrEntity.toString else nullString)
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//    override def title: String = "eTarget"
//
//    override def description = "An eTarget is a target that pinpoints the head of a noun phrase of event phrase. TODO()"
//  }
//
//  def sTargetCanRender(x: STarget): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.spanStr,
//      "# eTargets extracted" → (x.parent match {
//        case tf: TargetFrame ⇒ tf.sTargets.find(s ⇒ s.id == x.id).get.eTargets.size.toString
//        case ose: ObjectiveSpeechEvent ⇒ "0"
//      })
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//
//    override def title: String = "sTarget"
//    override def description = "An sTarget is a target that simply marks a span of text, and (TODO)"
//  }
//
//  def targetFrameCanRender(x: TargetFrame): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "# of sTargets" → x.sTargets.size.toString,
//      "# of old eTargets" → x.oldETargets.size.toString,
//      "# of new eTargets" → x.newETargets.size.toString
//    )
//
//    override def title: String = "Target Frame"
//    override def description = "A target frame is a collection of targets (both eTargets and sTargets)."
//  }
//
//  def objectiveSpeechEventCanRender(x: ObjectiveSpeechEvent): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.spanStr,
//      "Nested source" → x.nestedSource.mkString(", ")
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//
//    override def title: String = "Obj Speech Event"
//    override def description = "An objective speech event is a proposition attributed to a source, in the absensce of any non-belief private states attributed to the source."
//  }
//
//  def expressiveSubjectivityCanRender(x: ExpressiveSubjectivity): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.spanStr,
//      "Nested source" → x.nestedSource.mkString(", "),
//      "Intensity" → (if (x.intensity != null) x.intensity.toString else nullString),
//      "Polarity" → (if (x.polarity != null) x.polarity.toString else nullString),
//      "Insubstantiality" → (if (x.insubstantiality != null) x.insubstantiality.toString else nullString)
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//    override def title: String = "Expr Subj"
//    override def description = "An expressive subjectivity expression is the particular wording used to express private states, and not to refer to private states or speech events. "
//  }
//
//  def directSubjectiveCanRender(x: DirectSubjective): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.spanStr,
//      "Nested source" → x.nestedSource.mkString(", "),
//      "Intensity" → (if (x.intensity != null) x.intensity.toString else nullString),
//      "Expression intensity" → (if (x.expressionIntensity != null) x.expressionIntensity.toString else nullString),
//      "Insubstantiality" → (if (x.insubstantiality != null) x.insubstantiality.toString else nullString)
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//    override def title: String = "Direct Subj"
//    override def description = "A direct subjective is an direct mention of private states or a speech event expressing private states."
//  }
//
//  def attitudeCanRender(x: Attitude): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.spanStr,
//      "Nested source" → x.nestedSource.mkString(", "),
//      "Type" → (if (x.attitudeType != null) x.attitudeType.toString else nullString),
//      "Intensity" → (if (x.intensity != null) x.intensity.toString else nullString),
//      "Polarity" → (if (x.polarity != null) x.polarity.toString else nullString)
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//    override def title: String = "Attitude"
//    override def description = "An attitude is (TODO). "
//  }
//
//  def sentenceCanRender(x: Sentence): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.spanStr
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//    override def title: String = "Sentence"
//    override def description = "A sentence is a collection of annotations (the SubjObjs). "
//  }
//
//  def documentCanRender(x: Document): PropertyTableFormatter = new PropertyTableFormatter {
//    override def content: ListMap[String, String] = ListMap(
//      "Span" → x.briefSpanStr
//    )
//    override def propertyValueStyle(propertyName: String): TextStyle = {
//      propertyName match {
//        case "Span" ⇒ Italic
//        case _ ⇒ Default
//      }
//    }
//    override def title: String = "Document"
//    override def description = "A document is a collection of sentences. "
//  }
//}