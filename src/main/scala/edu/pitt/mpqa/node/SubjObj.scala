package edu.pitt.mpqa.node

import edu.pitt.mpqa.core.Span
import edu.pitt.mpqa.option.Insubstantiality

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
trait SubjObj {
  def parent: Sentence
  def span: Span
  def nestedSource: NestedSource
  def insubstantiality: Insubstantiality

  //region Java Getters and Setters
  def getParent: Sentence = parent
  def getSpan: Span = span
  def getNestedSource = nestedSource
  def getInsubstantiality = insubstantiality
  //endregion
}
