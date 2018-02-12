package repositories

import com.google.inject.ImplementedBy
import models.{Todo, User}

@ImplementedBy(classOf[AnormTodoRepository])
trait TodoRepository {
  def ofUser(user: User): Set[Todo]
}
