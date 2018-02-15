package repositories

import java.util.UUID

import com.google.inject.ImplementedBy
import models.{Todo, User}

@ImplementedBy(classOf[AnormTodoRepository])
trait TodoRepository {
  def ofUser(user: User): List[Todo]

  def save(user: User, title: String, content: String): Unit = {
    save(user, Todo(UUID.randomUUID(), title, content))
  }

  def save(user: User, todo: Todo): Unit

  def update(user: User, todo: Todo): Unit
}
