package actor

import akka.actor.{Actor, ActorRef, Props}

class Room(name: String) extends Actor {
  type Pointer = (Int, Int)

  var users: Map[ActorRef, Pointer] = Map()

  //  val contents: List[StringBuilder] = List(new StringBuilder("Hello"))

  val contents: List[StringBuilder] =
    """Lorem ipsum dolor sit amet, consectetur adipiscing elit.
       Curabitur imperdiet justo quis velit condimentum, vitae fermentum tortor tristique.
       Pellentesque elementum tortor sit amet venenatis cursus. Nunc iaculis, mauris eget congue consectetur,
       orci diam pulvinar diam, quis imperdiet metus diam sed lacus. Phasellus in orci sodales, bibendum nunc et,
       imperdiet turpis. Fusce nec purus aliquam, tempor lacus vel, faucibus dui. Interdum et malesuada fames
       ac ante ipsum primis in faucibus. Pellentesque habitant morbi tristique senectus et netus et malesuada
       fames ac turpis egestas. Pellentesque eu sollicitudin diam. Maecenas vel auctor erat, eu mattis ante.
       Morbi vitae adipiscing turpis. Pellentesque id laoreet neque. Praesent posuere massa metus, gravida molestie
       felis pretium eu. Fusce aliquam, metus eget fringilla congue, purus nulla cursus ligula,
       et porttitor risus velit et eros.
    """.stripMargin.split("\n").toList.map(new StringBuilder(_))

  override def receive: Receive = {
    case UserJoin(user) =>
      users += (user ->(0, 0))
      user ! RoomResponse(name, self)
    case GetContents() =>
      sender ! Contents(contents.toString())
  }
}

object Room {
  def props(name: String) = Props(classOf[Room], name)
}