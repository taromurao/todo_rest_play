package models

import java.util.UUID

import play.api.libs.json.{Json, Reads, Writes}

object Todo {
  implicit val todoReads: Reads[Todo] = Json.reads[Todo]
  implicit val todoWrites: Writes[Todo] = Json.writes[Todo]
}

case class Todo(id: UUID, title: Title, content: Content)

