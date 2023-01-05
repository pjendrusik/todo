package com.softwaremill.bootzooka.admin

import cats.data.NonEmptyList
import cats.effect.IO
import com.softwaremill.bootzooka.http.Http
import com.softwaremill.bootzooka.infrastructure.Doobie._
import com.softwaremill.bootzooka.infrastructure.Json._
import com.softwaremill.bootzooka.security.{ApiKey, Auth}
import com.softwaremill.bootzooka.todo.{ToDoEntity, ToDoService}
import com.softwaremill.bootzooka.user.{User, UserService}
import com.softwaremill.bootzooka.util.ServerEndpoints
import doobie.util.transactor.Transactor

class AdminApi(http: Http, auth: Auth[ApiKey], toDoService: ToDoService, userService: UserService, xa: Transactor[IO]) {
  import AdminApi._
  import http._

  // TODO: add ADMIN role to User and validate during authentication
  private val authedEndpoint = secureEndpoint.serverSecurityLogic(authData => auth(authData).toOut)

  private val allToDosEndpoint = authedEndpoint.get
    .in("todos")
    .out(jsonBody[List[ToDoEntity]])
    .serverLogic(_ => (_: Unit) => (for {
      todos <- toDoService.getAll().transact(xa)
    } yield todos).toOut)

  private val allUsersEndpoint = authedEndpoint.get
    .in("users")
    .out(jsonBody[List[User]])
    .serverLogic(_ => (_: Unit) => (
      for {
        users <- userService.getAll().transact(xa)
      } yield users
    ).toOut)

  val endpoints: ServerEndpoints =
    NonEmptyList.of(allToDosEndpoint, allUsersEndpoint).map(_.tag("admin"))

}

object AdminApi {

}
