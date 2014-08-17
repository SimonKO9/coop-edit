package document

import scala.collection.immutable.Seq

class Document(val lines: Seq[String]) {
  def putChar(x: Int, y: Int, char: Char) = {
    if (lines.size <= y) {
      val padEmptyLines = for (x <- lines.size to y - 1) yield ""
      val appendedLine = (" " * x) + char
      Document(lines ++ (padEmptyLines :+ appendedLine))
    } else {
      val (before, line, after) = DocumentUtil.partitionIntoThree(lines, y)
      val lineUpdated = (line.take(x) ++ (char +: line.drop(x))).mkString("")
      Document(before ++ (lineUpdated +: after))
    }
  }

  def delChar(x: Int, y: Int) = {
    if (lines.size <= y) {
      this
    } else {
      val (before, line, after) = DocumentUtil.partitionIntoThree(lines, y)
      if (x == 0) {
        if (y == 0) {
          this
        } else {
          val (beforeWithoutLast, last) = before.splitAt(before.size - 1)
          val lineUpdated = last.head + line
          Document(beforeWithoutLast ++ (lineUpdated +: after))
        }
      } else {
        val lineUpdated = line.take(x - 1) ++ line.drop(x)
        Document(before ++ (lineUpdated +: after))
      }
    }
  }

  override def equals(other: scala.Any): Boolean = other match {
    case doc: Document => doc.lines == lines
    case _ => false
  }

  override def toString: String = lines.mkString("\n")
}

object Document {
  def apply(lines: Seq[String]) = new Document(lines)
}
