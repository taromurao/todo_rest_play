package repositories
import java.sql.Connection
import javax.inject.Inject

import anorm._
import models.{Todo, User}
import play.api.db.Database

class AnormTodoRepository @Inject()(db: Database) extends TodoRepository {
  override def ofUser(user: User): List[Todo] = {
    val query: SqlQuery = SQL(s"SELCT * from todos WHERE user_id='${user.id}'")

    db.withConnection[List[Todo]] { implicit conn: Connection => query.as(todoParser.*) }
  }

  override def save(user: User, todo: Todo): Unit = {
    val query: SqlQuery = SQL(s"""
      INSERT INTO todos (id, title, content, user_id)
      VALUES ('${todo.id}', '${todo.title}', '${todo.content}', '${user.id}')
        """)

    db.withConnection { implicit conn: Connection => query.execute() }
  }

  private val todoParser: RowParser[Todo] = Macro.namedParser[Todo]
}
