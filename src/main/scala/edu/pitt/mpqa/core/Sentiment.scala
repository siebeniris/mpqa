package edu.pitt.mpqa.core

import edu.pitt.mpqa.node._
import edu.pitt.mpqa.option._

/**
 * Created by Yuhuan Jiang (jyuhuan@gmail.com) on 12/2/15.
 */
case class Sentiment(source: ImmediateSourceMention, target: Span, polarity: Polarity)
