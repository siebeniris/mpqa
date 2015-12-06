package edu.pitt.mpqa.node

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
trait HasETarget {
  def eTargets: Seq[ETarget]
}
