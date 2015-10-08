package edu.pitt.mpqa.node

import scala.collection.JavaConverters._

/**
 * Represents a target frame.
 *
 * In the current version of MPQA, a target frame can only appear in (i.e., belong to)
 * two kinds of annotations:
 *
 *  - An attitude of a direct subjective
 *  - An expressive subjectivity element
 *
 *  Although according to the structure of this class, it looks like every target frame
 *  contains both sTargets and eTargets. However, this is not always true, based on what
 *  the `parent` is:
 *
 *   - If the `parent` is an `Attitude` whose type is sentiment,
 *   then both `sTargetsAndETargets` and `newETargets` are valid.
 *   - If the `parent` is an `Attitude` whose type is not sentiment,
 *   then only `sTargetsAndETargets` is valid (the second element of each of its pairs is null).
 *   A convenient method `sTargets` is provided to only return a list of sTargets.
 *   - If the `parent` is an `ExpressiveSubjectivity`, then only the `newETargets` is valid.
 *
 * @param parent The annotation that this target frame belongs to.
 *               Currently, the only possible types of parent are
 *               [[Attitude Attitude]] and
 *               [[ExpressiveSubjectivity ExpressiveSubjectivity]].
 * @param id The ID of this target frame.
 * @param sTargetsAndETargets A mapping from sTargets to their extracted eTargets
 *                            in this target frame. The sTargets are those annotated in MPQA 2.
 *                            Each item is a pair of
 *                             - An sTarget
 *                             - A collection of eTargets extracted from the sTarget.
 * @param newETargets The eTargets annotated in MPQA 3.
 */
class TargetFrame(val parent: HasTargetFrame,
                  val id: String,
                  var sTargets: Seq[STarget],
                  var newETargets: Seq[ETarget]) extends HasSTarget {

  def sentence = parent match {
    case att: Attitude ⇒ att.sentence
    case ese: ExpressiveSubjectivity ⇒ ese.sentence
  }

  /**
   * Gets all eTargets.
   */
  def eTargets: Seq[ETarget] = oldETargets ++ newETargets

  /**
   * Gets only eTargets in MPQA 2.
   */
  def oldETargets: Seq[ETarget] = sTargets.flatMap(_.eTargets)

  //region Java Compatibility Methods
  def getParent = parent
  def getId = id
  def getNewETargets: java.util.List[ETarget] = newETargets.asJava
  def getOldETargets: java.util.List[ETarget] = oldETargets.asJava
  def getETargets: java.util.List[ETarget] = eTargets.asJava
  //endregion

  override def toString = id

  override def children = newETargets ++ sTargets
}

