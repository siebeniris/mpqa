package edu.pitt.mpqa.node

import edu.pitt.mpqa.node.Span


/**
 * Represents an agent mention.
 * @param identifier The ID of the agent.
 *           Usually a meaningful abbreviation.
 *           Unique within a document.
 * @param span The text span of this agent.
 *             (TODO: Is it the span of the first agent mention among all mentions?)
 */
case class Agent(identifier: String, span: Span) {
  def spanStr = identifier
  override def toString = spanStr
}
