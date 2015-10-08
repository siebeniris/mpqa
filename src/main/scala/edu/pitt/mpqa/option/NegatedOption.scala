package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/21/15.
 */
sealed trait NegatedOption
object NegatedOption {
  object Yes extends NegatedOption { override def toString = "Yes" }
  object No extends NegatedOption { override def toString = "No" }
}
