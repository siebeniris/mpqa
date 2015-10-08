package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/16/15.
 */
sealed trait Polarity

object Polarity {
  object UncertainNeutral extends Polarity { override def toString = "UncertainNeutral" }
  object Sentiment extends Polarity { override def toString = "Sentiment" }
  object Negative extends Polarity { override def toString = "Negative" }
  object Positive extends Polarity { override def toString = "Positive" }
  object Both extends Polarity { override def toString = "Both" }
  object Neutral extends Polarity { override def toString = "Neutral" }
  object UncertainPositive extends Polarity { override def toString = "UncertainPositive" }
  object UncertainNegative extends Polarity { override def toString = "UncertainNegative" }
  object UncertainBoth extends Polarity { override def toString = "UncertainBoth" }
  object Attitude extends Polarity { override def toString = "Attitude" }
}
