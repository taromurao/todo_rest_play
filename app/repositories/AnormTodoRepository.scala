package repositories
import java.sql.Connection
import javax.inject.Inject

import anorm._
import models.{Todo, User}
import play.api.db.Database

class AnormTodoRepository @Inject()(db: Database) extends TodoRepository {
  override def ofUser(user: User): Set[Todo] = ???

  override def save(user: User, todo: Todo): Unit = {
    val query: SqlQuery = SQL(s"""
      INSERT INTO todos (id, title, content, user_id)
      VALUES ('${todo.id}', '${todo.title}', '${todo.content}', '${user.id}')
        """)

    db.withConnection { implicit conn: Connection => query.execute() }
  }
}
