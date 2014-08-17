package actor

import akka.actor.{ActorLogging, Actor, ActorRef, Props}

class RoomSupervisor extends Actor with ActorLogging {
  type RoomRef = ActorRef

  var rooms: Map[String, RoomRef] = Map()

  override def receive: Receive = {
    case GetOrCreateRoom(name) =>
      rooms.get(name) match {
        case Some(room) =>
          room ! UserJoin(sender())
        case None =>
          val room = createRoom(name)
          room ! UserJoin(sender())
      }
    case ListRooms() =>
      sender ! ListRoomsResponse(rooms.keys.toSeq)
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