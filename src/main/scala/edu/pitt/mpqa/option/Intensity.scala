package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/16/15.
 */
sealed trait Intensity

object Intensity {
  object Low extends Intensity { override def toString = "Low" }
  object LowMedium extends Intensity { override def toString = "LowMedium" }
  object Medium extends Intensity { override def toString = "Medium" }
  object MediumHigh extends Intensity { override def toString = "MediumHigh" }
  object High extends Intensity { override def toString = "High" }
  object HighExtreme extends Intensity { override def toString = "HighExtreme" }
  object Extreme extends Intensity { override def toString = "Extreme" }
  object Neutral extends Intensity { override def toString = "Neutral" }
}
