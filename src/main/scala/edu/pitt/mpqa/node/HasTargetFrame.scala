package edu.pitt.mpqa.node

/**
  * @author Yuhuan Jiang (jyuhuan@gmail.com).
  */
trait HasTargetFrame {
  def targetFrame: TargetFrame
  def sentence: Sentence
  def getSentence: Sentence = sentence
}
