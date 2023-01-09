package com.softwaremill.bootzooka.todo

import cats.data.NonEmptyList
import cats.effect.IO
import com.softwaremill.bootzooka.http.Http
import com.softwaremill.bootzooka.infrastructure.Doobie._
import com.softwaremill.bootzooka.infrastructure.Json._
import com.softwaremill.bootzooka.security.{ApiKey, Auth}
import com.softwaremill.bootzooka.util.ServerEndpoints
import doobie.util.transactor.Transactor

case class ToDoApi(http: Http, auth: Auth[ApiKey], toDoService: ToDoService, xa: Transactor[IO]) {
  import ToDoApi._
  import http._

  private val ToDoPath = "todo"

  private val authedEndpoint = secureEndpoint.serverSecurityLogic(authData => auth(authData).toOut)

  private val getUserTODOsEndpoint = authedEndpoint.get
    .in(ToDoPath)
    .out(jsonBody[List[ToDoEntity]])
    .serverLogic(userId =>
      (_ :Unit) =>
        (for {
        todos <- toDoService.findByUserId(userId).transact(xa)
        _ = println(todos)
      } yield todos).toOut
    )

  private val newToDoEndpoint = authedEndpoint.post
    .in(ToDoPath)
    .in(jsonBody[NewToDo])
    .out(jsonBody[ToDoEntity])
    .serverLogic { userId => request =>
      (for {
        todo <- toDoService.newToDo(userId, request).transact(xa)
      } yield todo).toOut
    }


  val endpoints: ServerEndpoints =
    NonEmptyList.of(
      getUserTODOsEndpoint,
      newToDoEndpoint
    ).map(_.tag("todo"))
}


object ToDoApi {
  case class NewToDo(content: String)
}
