import java.util.UUID

import models.{Email, Todo, User}

package object testHelpers {
  val todo1: Todo = Todo(UUID.fromString("5bbbc575-a22a-41fb-9ae5-5843b1f722c5"), "a todo", "Do this.")

  val todo2: Todo = Todo(UUID.fromString("49a08cc0-49c7-4c5d-a8a6-1319d1ebfa95"), "another todo", "This is a tough one.")

  val todo1update: Todo = Todo(todo1.id, "new title", "new content")

  val A_USER_ID: UUID = UUID.fromString("6b18a6d3-dcd1-4652-9c94-8ef80e5e08ff")

  val AN_ENAIL: Email = "john@example.com"

  val A_PASSWORD: String = "9872938pp9q8jro38"

  val A_SALT: String = "91823nhfkasfd"

  val A_USER: User = User(A_USER_ID, AN_ENAIL, A_PASSWORD, A_SALT)
}
