package edu.pitt.mpqa.node

/**
 * Represents an agent mention.
 * @param identifier The ID of the agent.
 *           Usually a meaningful abbreviation.
 *           Unique within a document.
 * @param span The text span of this agent.
 *             (TODO: Is it the span of the first agent mention among all mentions?)
 */
case class Agent(identifier: String, span: Span) {
  /**
   * The string of this agent.
   * @return
   */
  def spanStr = identifier

  //region Java Compatibility Methods
  /**
   * Gets the ID of the agent.
   * Usually a meaningful abbreviation.
   * Unique within a document.
   */
  def getIdentifier = identifier

  /**
   * Gets the text span of this agent.
   * (TODO: Is it the span of the first agent mention among all mentions?)
   */
  def getSpan = span

  /**
   * Gets string of this agent.
   */
  def getSpanStr = spanStr
  //endregion

  override def toString = spanStr
}
