package document

import scala.collection.immutable.Seq

object DocumentUtil {
  def partitionIntoThree(lines: Seq[String], y: Int) = {
    val before = lines.take(y)
    val line = lines(y)
    val after = lines.drop(y + 1)
    (before, line, after)
  }
}
