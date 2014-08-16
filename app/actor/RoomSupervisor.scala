package actor

import akka.actor.{Actor, ActorRef, Props}

class RoomSupervisor extends Actor {
  type RoomRef = ActorRef

  var rooms: Map[String, RoomRef] = Map()

  override def receive: Receive = {
    case GetOrCreateRoom(name) =>
      rooms.get(name) match {
        case Some(room) => RoomResponse(name, room)
        case None =>
          val room = createRoom(name)
          room ! UserJoin(sender())
      }
  }

  private def createRoom(name: String) = {
    val roomActor = context.actorOf(Room.props(name))
    rooms += (name -> roomActor)
    roomActor
  }
}

object RoomSupervisor {
  def props = Props(classOf[RoomSupervisor])
}