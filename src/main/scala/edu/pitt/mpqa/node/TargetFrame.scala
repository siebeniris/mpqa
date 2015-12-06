package edu.pitt.mpqa.node

import scala.collection.JavaConverters._

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
class TargetFrame(var id: String,
                  var sTargets: Seq[STarget],
                  var newETargets: Seq[ETarget],
                  var parent: HasTargetFrame) extends HasETarget { self =>
  def oldETargets = sTargets.flatMap(_.eTargets)
  def eTargets = oldETargets ++ newETargets

  //region Java Getters and Setters
  def getId: String = id
  def getSTargets: java.util.List[STarget] = sTargets.asJava
  def getNewETargets: java.util.List[ETarget] = newETargets.asJava
  def getParent: HasTargetFrame = parent

  def setId(id: String): Unit = self.id = id
  def setSTargets(sTargets: java.util.List[STarget]): Unit = self.sTargets = sTargets.asScala
  def setNewETargets(newETargets: java.util.List[ETarget]): Unit = self.newETargets = newETargets.asScala
  def setParent(parent: HasTargetFrame): Unit = self.parent = parent
  //endregion

}
