package models

import scalaz._
import Scalaz._
import play.api.libs.json._
import play.api.data._
import play.api.data.format.Formatter

/**
 * Provides a type class for useful enumerations
 *
 * @example
 * {{{
 *  import play.api.libs.json_
 *  import play.api.data.format.Formatter
 *  import scalaz.Enum
 *
 *  object foo {
 *    sealed trait Foo
 *    case object Bar     extends Foo
 *    case object Foobar  extends Foo
 *
 *    val vs: Set[Foo] = Set(Bar, Foobar)
 *
 *    implicit val fooEnum: Enum[Foo] = enums.enumFromList(vs.toList)
 *    implicit val fooReads: Reads[Foo] = enums.jsonReads(x => vs.find(v => v.toString == x))
 *    implicit val fooWrites: Writes[Foo] = enums.jsonWrites(_.toString)
 *    implicit val fooFormatter: Formatter[Foo] = enums.enumFormat[Foo](s =>
 *      vs.find(_.toString == s)
 *    )
 *  }
 * }}}
 */
object enums {
  def enumFromList[A](vs: List[A]): Enum[A] = {
    val size = vs.size
    new Enum[A] {
      def pred(x: A) = {
        val idx = vs.indexOf(x) + 1
        vs(if(idx < size) idx else 0)
      }
      def succ(x: A) = {
        val idx = vs.indexOf(x) - 1
        vs(if(idx >= 0) idx else size - 1)
      }
      def order(x: A, y: A) = vs.indexOf(x) ?|? vs.indexOf(y)
      override def min = vs.headOption
      override def max = vs.lastOption
    }
  }

  def toSnakeCase(name: String) = {
    val words: List[String] = name.foldLeft(List[String]()){ (words, char) =>
      if(char.isUpper) {
        char.toLower.toString :: words
      } else {
        words match {
          case h :: t => (h + char.toLower.toString) :: t
          case _ => List(char.toLower.toString)
        }
      }
    }
    words.reverse.mkString("-")
  }

  def toCamelCase(name: String) = name.split("-").toList.map(_.capitalize).mkString

  def jsonWrites[A](vToString: (A => String), transformer: (String => String) = toSnakeCase _): Writes[A] = new Writes[A] {
    def writes(v: A) = {
      val s1: String = vToString(v)
      val s2: String = transformer(s1)
      JsString(s2)
    }
  }

  def jsonReads[A](parser: String => Option[A], transformer: String => String = toCamelCase _): Reads[A] = new Reads[A] {
    def reads(js: JsValue) = js match {
      case JsString(s) => parser(transformer(s)).map(JsSuccess(_)).getOrElse(JsError("no parse"))
      case _ => JsError("no parse")
    }
  }

  def enumFormat[A](
    parser: String => Option[A],
    scalaToForm: String => String = toSnakeCase _,
    formToScala: String => String = toCamelCase _
  ): Formatter[A] = new Formatter[A] {
    def bind(key: String, data: Map[String, String]) =
      data
        .get(key)
        .flatMap(x => parser(formToScala(x)))
        .toRight(Seq(FormError(key, "error.invalid", Nil)))
    def unbind(key: String, value: A) = Map(key -> scalaToForm(value.toString))
  }
}
