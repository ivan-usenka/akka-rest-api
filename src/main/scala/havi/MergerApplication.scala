package havi

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{complete, logRequestResult, path, pathPrefix, _}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContextExecutor

object MergerApplication extends App {
  val config = ConfigFactory.load()
  val host = config.getString("http.host") // Gets the host and a port from the configuration
  val port = config.getInt("http.port")

  implicit val system: ActorSystem = ActorSystem() // ActorMaterializer requires an implicit ActorSystem
  implicit val ec: ExecutionContextExecutor = system.dispatcher // bindingFuture.map requires an implicit ExecutionContext


  implicit val materializer: ActorMaterializer = ActorMaterializer()

  Http().bindAndHandle(routes, config.getString("http.host"), config.getInt("http.port"))

  def routes = logRequestResult("akka-http-merger") {
    pathPrefix("api") {
      path("merger") {
        get {
          val response: String = mergeFiles()
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Say hello to $response-akka-http</h1>"))
        }
      }
    }
  }

  def mergeFiles(): String = {
    "doing-merge"
  }
}
