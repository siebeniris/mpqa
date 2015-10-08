package edu.pitt.mpqa.node

/**
 * Represents an annotation that has a target frame.
 *
 * This trait is necessary because the class `TargetFrame` needs a `parent`.
 *
 * Currently, those that can have a target frame is listed below:
 *  - an attitude ([[Attitude Attitude]]) of a ([[DirectSubjective DirectSubjective]])
 *  - an expressive subjectivities ([[ExpressiveSubjectivity ExpressiveSubjectivity]])
 *
 * To make extensions, future types of annotations must conform to this trait.
 *
 */
trait HasTargetFrame extends Annotation {
  def targetFrame: TargetFrame

  //region Java Compatibility Methods
  def getTargetFrame = targetFrame
  //endregion

  //region Conforming to Annotation
  override def children: Seq[Annotation] = Seq(targetFrame)
  //endregion

}
