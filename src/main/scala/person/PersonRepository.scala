package person

import java.util.UUID

import faker._

class PersonRepository {

  private var people = Map.empty[String, Person]

  for(i <- 1 to 99){
    val image = s"https://randomuser.me/api/portraits/men/$i.jpg"
    val id = UUID.randomUUID().toString
    val name = Name
    val person = Person(id, name.first_name, name.last_name, Internet.email, PhoneNumber.phone_number, image)
    people += (id -> person)
  }



  def get = people

  def getOne(id: String) = people.get(id)

  def create(station: Person) = people += (station.id -> station)

  def update(id: String, station: Person) = people += (id -> station)

  def delete(id: String) = people -= id
}
