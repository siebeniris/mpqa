package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/19/15.
 */
sealed trait ETargetType

object ETargetType {
  object Event extends ETargetType { override def toString = "Event" }
  object Entity extends ETargetType { override def toString = "Entity" }
}
