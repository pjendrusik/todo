package com.softwaremill.bootzooka.todo

import cats.implicits._
import com.softwaremill.bootzooka.infrastructure.Doobie._
import com.softwaremill.bootzooka.user.User
import com.softwaremill.bootzooka.util._
import com.softwaremill.tagging.@@

import java.time.Instant

class ToDoService(todoModel: ToDoModel, idGenerator: IdGenerator, clock: Clock) {

  def newToDo(userId: Id @@ User, request: ToDoApi.NewToDo): ConnectionIO[ToDoEntity] = {
    for {
      id <- idGenerator.nextId[ConnectionIO, ToDoEntity]()
      todo = ToDoEntity(id, userId, request.content, Instant.now())
      _ <- todoModel.insert(todo)
    } yield todo
  }

  def findByUserId(userId: Id @@ User): ConnectionIO[List[ToDoEntity]] =
    todoModel.findByUserId(userId)

  def getAll(): ConnectionIO[List[ToDoEntity]] =
    todoModel.getAll()

}
