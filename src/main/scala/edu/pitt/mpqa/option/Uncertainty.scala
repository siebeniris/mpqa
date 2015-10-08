package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/16/15.
 */
sealed trait Uncertainty

object Uncertainty {
  object SomewhatUncertain extends Uncertainty { override def toString = "SomewhatUncertain" }
}
