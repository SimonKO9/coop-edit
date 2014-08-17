package controllers

import actor._
import akka.actor.ActorRef
import akka.pattern._
import akka.util.Timeout
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.libs.json.{JsString, Json}
import play.api.mvc.WebSocket.FrameFormatter
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationDouble

object RoomController extends Controller {

  private val roomSupervisor: ActorRef = Akka.system.actorOf(RoomSupervisor.props)

  private implicit def msgFormatter: FrameFormatter[ClientMessage] = WebSocket.FrameFormatter.jsonFrame.transform(
    msg =>
      ClientMessage.writes.writes(msg),
    json => {
      val tryParse = ClientMessage.reads.reads(json)
      if (tryParse.isError)
        throw new RuntimeException("Unexpected message: " + json)
      else
        tryParse.get
    }
  )

  def ws = WebSocket.acceptWithActor[ClientMessage, ClientMessage] { request =>
    out => User.props(out, roomSupervisor)
  }

  def rooms = Action.async { request =>
    implicit val timeout = Timeout(5 seconds)
    (roomSupervisor ? ListRooms()).map {
      case ListRoomsResponse(rooms) =>
        val roomNamesJson = rooms.map(room => JsString(room))
        Ok(Json.toJson(roomNamesJson))
    }.recover {
      case ex: AskTimeoutException => InternalServerError("")
    }
  }
}
