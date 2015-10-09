package edu.pitt.mpqa.node

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 10/9/15.
 *
 * Two possible `HasETarget`s:
 * - TargetFrame
 * - STarget
 *
 */
trait HasETarget extends Annotation {
  /**
   * The eTargets contained in this annotation.
   */
  def eTargets: Seq[ETarget]
}
