package repositories
import models.{Todo, User}

class AnormTodoRepository extends TodoRepository {
  override def ofUser(user: User): Set[Todo] = ???

  override def save(user: User, todo: Todo): Unit = ???
}
