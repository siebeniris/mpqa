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
  def parent: Annotation
  def children: Iterable[Annotation]
}
