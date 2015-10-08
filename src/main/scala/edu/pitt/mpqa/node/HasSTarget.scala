package edu.pitt.mpqa.node

/**
 * A trait that unifies ObjectiveSpeechEvent and TargetFrame.
 * This is necessary for the type of an sTarget's parent.
 */
trait HasSTarget extends Annotation {
  def sTargets: Seq[STarget]

  //region Java Compatibility Methods
  def getSTargets = sTargets
  //endregion

  //region Conforming to Annotation
  override def children: Seq[Annotation] = sTargets
  //endregion

}
