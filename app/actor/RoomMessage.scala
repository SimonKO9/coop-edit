package actor

import akka.actor.ActorRef

sealed trait RoomMessage

case class GetOrCreateRoom(name: String) extends RoomMessage

case class RoomResponse(name: String, actor: ActorRef) extends RoomMessage

case class GetContents() extends RoomMessage

case class Contents(str: String) extends RoomMessage

case class UserJoin(client: ActorRef) extends RoomMessage

case class ListRooms() extends RoomMessage

case class ListRoomsResponse(rooms: Seq[String]) extends RoomMessage