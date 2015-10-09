package edu.pitt.mpqa.node

/**
 * A trait that unifies ObjectiveSpeechEvent and TargetFrame.
 * This is needed to represent an sTarget's parent.
 * An sTarget's parent can be either ObjectiveSpeechEvent or TargetFrame.
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
