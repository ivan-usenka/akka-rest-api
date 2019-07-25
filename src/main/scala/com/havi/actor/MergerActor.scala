package com.havi.actor

import akka.actor.{Actor, ActorLogging, Props}

case class MergeRequest()

object MergerActor {
  def props() = Props(new MergerActor())
}

class MergerActor extends Actor with ActorLogging{
  override def receive: Receive = {
    case mergeRequest: MergeRequest =>
      mergeFiles()

    case unknown => println(s"got Unknown message: $unknown")
  }

  private def mergeFiles(): Unit = {
    println("Merge started")
  }
}
