package person

final case class Person(id: String, firstName: String, lastName : String, email : String, phone : String, avatar: String)

case class PersonNotFound(id :String) extends Exception(s"Person with $id doesn't exists")

case class PersonAlreadyExists(id :String) extends Exception(s"Person with $id already exists")