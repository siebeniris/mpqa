package edu.pitt.mpqa.option

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 6/18/15.
 */
sealed trait Insubstantiality
object Insubstantiality {
  object C1 extends Insubstantiality { override def toString = "C1" }
  object C2 extends Insubstantiality { override def toString = "C2" }
  object C3 extends Insubstantiality { override def toString = "C3" }
}
