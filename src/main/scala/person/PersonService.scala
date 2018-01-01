package person

import scala.util.Try

class PersonService(repository: PersonRepository) {
  def get: List[Person] = repository.get.values.toList

  def getOne(id: String) = {
    Try {
      repository.getOne(id).getOrElse(throw PersonNotFound(id))
    }
  }

  def create(person: Person) = {
    val id = person.id
    Try {
      repository.getOne(id)
        .map(_ => throw PersonAlreadyExists(id))
        .getOrElse {
          repository.create(person)
          person
        }
    }
  }

  def getByName(phrase: String) = {
    val personQuery: Person => Boolean = p => {
      p.firstName.toLowerCase().contains(phrase.toLowerCase()) || p.lastName.toLowerCase().contains(phrase.toLowerCase())
    }
    Try {
      repository.get.values
        .filter(personQuery)
        .toList
    }
  }

  def update(id: String, station: Person): Person = {
    repository.update(id, station)
    station
  }

  def delete(id: String) = {
    repository.delete(id)
  }
}
