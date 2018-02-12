package models

import play.api.libs.json.{Json, Reads, Writes}

object Todo {
  implicit val todoReads: Reads[Todo] = Json.reads[Todo]
  implicit val todoWrites: Writes[Todo] = Json.writes[Todo]
}

case class Todo(title: Title, content: Content)

