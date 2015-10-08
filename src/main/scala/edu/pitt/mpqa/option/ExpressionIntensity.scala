package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/16/15.
 */
sealed trait ExpressionIntensity

object ExpressionIntensity {
  object Low extends ExpressionIntensity { override def toString = "Low" }
  object Medium extends ExpressionIntensity { override def toString = "Medium" }
  object High extends ExpressionIntensity { override def toString = "High" }
  object Neutral extends ExpressionIntensity { override def toString = "Neutral" }
  object Extreme extends ExpressionIntensity { override def toString = "Extreme" }
}
