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
 *   then both `sTargets` and `newETargets` are valid.
 *   - If the `parent` is an `Attitude` whose type is not sentiment,
 *   then only `sTargets` is valid (the second element of each of its pairs is null).
 *   - If the `parent` is an `ExpressiveSubjectivity`, then only the `newETargets` is valid.
 *
 * @param parent The annotation that this target frame belongs to.
 *               Currently, the only possible types of parent are
 *               [[Attitude Attitude]] and
 *               [[ExpressiveSubjectivity ExpressiveSubjectivity]].
 * @param id The ID of this target frame.
 * @param sTargets The sTargets annotated in MPQA 2.
 *                 Each sTarget will also contain the newly identified eTargets within
 *                 the sTarget in MPQA 3. See [[STarget]] for details.
 * @param newETargets The eTargets annotated in MPQA 3 that are not inside any sTargets.
 */
class TargetFrame(val parent: HasTargetFrame,
                  val id: String,
                  var sTargets: Seq[STarget],
                  var newETargets: Seq[ETarget]) extends HasSTarget with HasETarget {


  /**
   * All eTargets, both those inside sTargets and those newly annotated.
   */
  def eTargets: Seq[ETarget] = oldETargets ++ newETargets

  /**
   * Only the eTargets inside the sTargets annotated in MPQA 2.
   */
  def oldETargets: Seq[ETarget] = sTargets.flatMap(_.eTargets)

  /**
   * The sentence to which this target frame belongs.
   */
  def sentence = parent match {
    case att: Attitude ⇒ att.sentence
    case ese: ExpressiveSubjectivity ⇒ ese.sentence
  }

  //region Java Compatibility Methods
  /**
   * The annotation that this target frame belongs to.
   * Currently, the only possible types of parent are
   * [[Attitude Attitude]] and
   * [[ExpressiveSubjectivity ExpressiveSubjectivity]].
   */
  def getParent = parent

  /**
   * Gets the ID of this target frame.
   */
  def getId = id

  /**
   * Gets the sTargets annotated in MPQA 2.
   * Each sTarget will also contain the newly identified eTargets within
   * the sTarget in MPQA 3. See [[STarget]] for details.
   */
  def getSTargets: java.util.List[STarget] = sTargets.asJava

  /**
   * The eTargets annotated in MPQA 3 that are not inside any sTargets.
   * @return
   */
  def getNewETargets: java.util.List[ETarget] = newETargets.asJava

  /**
   * Gets all eTargets, both those inside sTargets and those newly annotated.
   */
  def getETargets: java.util.List[ETarget] = eTargets.asJava

  /**
   * Gets only the eTargets inside the sTargets annotated in MPQA 2.
   */
  def getOldETargets: java.util.List[ETarget] = oldETargets.asJava

  /**
   * Gets the sentence to which this target frame belongs.
   */
  def getSentence = sentence
  //endregion

  override def toString = id

  override def children = newETargets ++ sTargets
}

