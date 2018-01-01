package api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import serializers.Formats._
import person.{Person, PersonAlreadyExists, PersonNotFound, PersonService}

import scala.util.{Failure, Success, Try}

class PersonController(personService: PersonService) {
  def route: Route = {
    pathPrefix("people") {
      routes
    }
  }

  private def routes = pathEndOrSingleSlash {
    get {
      parameters('phrase ? ""){ phrase =>
        complete(StatusCodes.OK -> personService.getByName(phrase))
      }

    } ~ post {
      entity(as[Person]) { person =>
        tryResponseHandling(personService.create(person))
      }
    }
  } ~ path(Segment) { id =>
    get {
      tryResponseHandling(personService.getOne(id))
    } ~ put {
      entity(as[Person]) { personToUpdate => {
        complete(StatusCodes.OK -> personService.update(id, personToUpdate))
      }
      }
    } ~ delete {
      personService.delete(id)
      complete(StatusCodes.NoContent)
    }
  }


  private def tryResponseHandling(resp: Try[Person]) = {
    resp match {
      case Success(s) => complete(StatusCodes.OK -> s)
      case Failure(e@PersonNotFound(_)) => complete(StatusCodes.NotFound -> e.getMessage)
      case Failure(e@PersonAlreadyExists(_)) => complete(StatusCodes.Conflict -> e.getMessage)
    }
  }
}
