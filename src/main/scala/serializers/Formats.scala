package serializers

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import person.Person

object Formats extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val personFormat = jsonFormat6(Person)
}
