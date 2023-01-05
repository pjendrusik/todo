package com.softwaremill.bootzooka.todo

import cats.implicits._
import com.softwaremill.bootzooka.infrastructure.Doobie._
import com.softwaremill.bootzooka.user.User
import com.softwaremill.bootzooka.util.Id
import com.softwaremill.tagging.@@

import java.time.Instant

class ToDoModel {

  def insert(todo: ToDoEntity): ConnectionIO[Unit] = {
    sql"""INSERT INTO todo (id, user_id, content, created_at)
         |VALUES (${todo.id}, ${todo.userId}, ${todo.content}, ${todo.createdAt})""".stripMargin.update.run.void
  }

  def findByUserId(userId: Id @@ User): ConnectionIO[List[ToDoEntity]] =
    findBy(fr"user_id = $userId").to[List]

  def getAll(): ConnectionIO[List[ToDoEntity]] =
    findBy(fr"1=1").to[List]

  private def findBy(by: Fragment): Query0[ToDoEntity] =
    (sql"SELECT id, user_id, content, created_at FROM todo WHERE " ++ by).query[ToDoEntity]

}

case class ToDoEntity (
  id: Id @@ ToDoEntity,
  userId: Id @@ User,
  content: String,
  createdAt: Instant
)
