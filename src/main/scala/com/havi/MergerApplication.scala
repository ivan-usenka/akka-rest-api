package com.havi

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, logRequestResult, path, pathPrefix, _}
import akka.stream.ActorMaterializer
import com.havi.actor.{MergeRequest, MergerActor}
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContextExecutor

object MergerApplication extends App {
  val config = ConfigFactory.load()
  val host = config.getString("http.host") // Gets the host and a port from the configuration
  val port = config.getInt("http.port")

  implicit val system: ActorSystem = ActorSystem() // ActorMaterializer requires an implicit ActorSystem
  implicit val ec: ExecutionContextExecutor = system.dispatcher // bindingFuture.map requires an implicit ExecutionContext

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val mergerActor = system.actorOf(MergerActor.props(), "AzureBlobFilesMerger")

  Http().bindAndHandle(routes, config.getString("http.host"), config.getInt("http.port"))

  def routes = logRequestResult("akka-http-merger") {
    pathPrefix("api") {
      path("merger") {
        get {
          proceedMergeFiles()
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Merging Started</h1>"))
        }
      }
    }
  }

  def proceedMergeFiles(): Unit = {
    mergerActor ! MergeRequest()
  }
}
