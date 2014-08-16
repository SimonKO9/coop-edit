package controllers

import actor._
import akka.actor.ActorRef
import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.mvc.WebSocket.FrameFormatter
import play.api.mvc._

object RoomController extends Controller {
  private val roomActor: ActorRef = Akka.system.actorOf(Room.props("room-name"))

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
}
