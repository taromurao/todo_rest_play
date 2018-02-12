import java.util.UUID

import models.{Email, Todo, User}

package object testHelpers {
  val todo1: Todo = Todo("a todo", "Do this.")

  val todo2: Todo = Todo("another todo", "This is a tough one.")

  val A_USER_ID: UUID = UUID.fromString("6b18a6d3-dcd1-4652-9c94-8ef80e5e08ff")

  val AN_ENAIL: Email = "john@example.com"

  val A_PASSWORD: String = "9872938pp9q8jro38"

  val A_SALT: String = "91823nhfkasfd"

  val A_USER: User = User(A_USER_ID, AN_ENAIL, A_PASSWORD, A_SALT)
}
