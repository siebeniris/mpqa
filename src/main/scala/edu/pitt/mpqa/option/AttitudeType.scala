package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/19/15.
 */
sealed trait AttitudeType
object AttitudeType {
  object Sentiment extends AttitudeType { override def toString = "Sentiment" }
  object Arguing extends AttitudeType { override def toString = "Arguing" }
  object Intention extends AttitudeType { override def toString = "Intention" }
  object Other extends AttitudeType { override def toString = "Other" }
  object Interior extends AttitudeType { override def toString = "Interior" }
  object Speculation extends AttitudeType { override def toString = "Speculation" }
  object Agree extends AttitudeType { override def toString = "Agree" }
}

