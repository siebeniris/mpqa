package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/21/15.
 */
sealed trait ReferredInSpanOption

object ReferredInSpanOption {
  object Yes extends ReferredInSpanOption { override def toString = "Yes" }
  object No extends ReferredInSpanOption { override def toString = "No" }
}
