package drmario

case class PassableBoard(elements: Seq[PassableElement], currentPill: PassableElement)     // making a case class because we are going to serialize this
