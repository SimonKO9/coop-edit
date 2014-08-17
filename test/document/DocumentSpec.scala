package document

import org.specs2.mutable._
import scala.collection.immutable.Seq

class DocumentSpec extends Specification {

  "Document" should {
    "contain put char for single-line doc" in {
      val lines = Seq("aaa")
      Document(lines).putChar(0, 0, 'x').lines === Seq("xaaa")
      Document(lines).putChar(1, 0, 'x').lines === Seq("axaa")
      Document(lines).putChar(3, 0, 'x').lines === Seq("aaax")
    }

    "contain put char for multi-line doc" in {
      val lines = Seq("aaa", "bbb", "ccc")
      Document(lines).putChar(0, 0, 'x').lines === Seq("xaaa", "bbb", "ccc")
      Document(lines).putChar(1, 1, 'x').lines === Seq("aaa", "bxbb", "ccc")
      Document(lines).putChar(2, 2, 'x').lines === Seq("aaa", "bbb", "ccxc")
    }

    "create new line if putting char out of document bounds" in {
      val lines = Seq("aaa")
      Document(lines).putChar(0, 1, 'x').lines === Seq("aaa", "x")
      Document(lines).putChar(0, 2, 'x').lines === Seq("aaa", "", "x")
      Document(lines).putChar(2, 2, 'x').lines === Seq("aaa", "", "  x")
    }

    "not contain removed character for single-line doc" in {
      val lines = Seq("abc")
      Document(lines).delChar(3, 0).lines === Seq("ab")
      Document(lines).delChar(1, 0).lines === Seq("bc")
    }

    "not contain removed character for multi-line doc" in {
      val lines = Seq("abc", "def")
      Document(lines).delChar(3, 0).lines === Seq("ab", "def")
      Document(lines).delChar(1, 1).lines === Seq("abc", "ef")
    }

    "merge lines when deleting character at beginning of line" in {
      val lines = Seq("abc", "def", "ghi")
      Document(lines).delChar(0, 1).lines === Seq("abcdef", "ghi")
    }

    "be same when trying to remove character at the beginning of document" in {
      val lines = Seq("abc", "def", "ghi")
      Document(lines).delChar(0, 0).lines === Document(lines).lines
    }

    "be same when trying to remove nonexistent character" in {
      val lines = Seq("abc", "def", "ghi")
      Document(lines).delChar(5, 0).lines === Document(lines).lines
      Document(lines).delChar(0, 5).lines === Document(lines).lines
    }
  }
}
