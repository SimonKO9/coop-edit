package actor

import play.api.libs.json._


sealed trait ClientMessage {
  def action: String
}

case class PutChar(char: String, action: String = "PutChar") extends ClientMessage

case class MovePointer(x: Int, y: Int, action: String = "MovePointer") extends ClientMessage

case class ClientJoin(room: String, action: String = "ClientJoin") extends ClientMessage

case class Sync(content: String, action: String = "Sync") extends ClientMessage

object ClientMessage {
  private val readers: Map[String, Reads[_]] = Map(
    "PutChar" -> Json.reads[PutChar],
    "ClientJoin" -> Json.reads[ClientJoin],
    "MovePointer" -> Json.reads[MovePointer]
  )

  implicit def reads: Reads[ClientMessage] = new Reads[ClientMessage] {
    override def reads(json: JsValue): JsResult[ClientMessage] = {
      val action = (json \ "action").as[JsString].value
      val parsed = readers.get(action).get.reads(json)
      parsed.asInstanceOf[JsResult[ClientMessage]]
    }
  }

  implicit def writes: Writes[ClientMessage] = new Writes[ClientMessage] {
    override def writes(message: ClientMessage): JsValue = message match {
      case m: PutChar => Json.writes[PutChar].writes(m)
      case m: ClientJoin => Json.writes[ClientJoin].writes(m)
      case m: MovePointer => Json.writes[MovePointer].writes(m)
      case m: Sync => Json.writes[Sync].writes(m)
    }
  }
}