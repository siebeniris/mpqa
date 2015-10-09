package edu.pitt.mpqa.node

/**
 * The base trait for all nodes in the tree for a document.
 *
 * Unifies the node for all annotations:
 *
 *   - DS
 *   - ESE
 *   - OSE
 *   - Attitude
 *   - TargetFrame
 *   - sTarget
 *   - eTarget
 *
 */
trait Annotation {
  /**
   * The parent of this annotation.
   * The parent of a `DirectSubjective` is a sentence. The parent of an `STarget` is `HasSTarget`.
   */
  def parent: Annotation
  def children: Iterable[Annotation]

}
