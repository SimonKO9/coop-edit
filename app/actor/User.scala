package actor

import akka.actor.{Actor, ActorRef, Props}

class User(out: ActorRef, roomSupervisor: ActorRef) extends Actor {
  override def receive: Receive = init

  def init: Receive = {
    case join: ClientJoin =>
      roomSupervisor ! GetOrCreateRoom(join.room)
    case RoomResponse(name, actor) =>
      context become joined(actor, name)
      actor ! GetContents()
  }

  def joined(roomActor: ActorRef, name: String): Receive = {
    case Contents(str) =>
      out ! Sync(str)
  }
}

object User {
  def props(ws: ActorRef, roomSupervisor: ActorRef) = Props(classOf[User], ws, roomSupervisor)
}