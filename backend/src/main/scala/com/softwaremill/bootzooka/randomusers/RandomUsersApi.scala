package com.softwaremill.bootzooka.randomusers

import cats.data.NonEmptyList
import cats.effect.IO
import com.softwaremill.bootzooka.http.Http
import com.softwaremill.bootzooka.infrastructure.Doobie._
import com.softwaremill.bootzooka.infrastructure.Json._
import com.softwaremill.bootzooka.security.{ApiKey, Auth}
import com.softwaremill.bootzooka.util.ServerEndpoints
import doobie.util.transactor.Transactor


case class User(firstName: String, email: String, password: String, birthDay: String)
object User {
  def apply(person: Person): User = User(
    firstName = person.firstName,
    email = person.email,
    password = person.password,
    birthDay = person.birthDate)
}

case class RandomUsersApi(http: Http, auth: Auth[ApiKey], randomUsersService: RandomUserService, xa: Transactor[IO]) {
  import http._

  private val UsersPath = "randomUsers"

  private val authedEndpoint = secureEndpoint.serverSecurityLogic(authData => auth(authData).toOut)

  private val getUserEndpoint = authedEndpoint.get
    .in(UsersPath)
    .out(jsonBody[List[User]])
    .serverLogic(_ => (_: Unit) => {
      for {
        _ <- randomUsersService.pullNewUsers().transact(xa)
        users <- randomUsersService.getUsers().transact(xa)
      } yield users.map(userEntry => User(
        firstName = userEntry.firstName,
        email = userEntry.email,
        password = userEntry.password,
        birthDay = userEntry.birthDay))
    }.toOut)

  private val createUserEndpoint = authedEndpoint.post
    .in(UsersPath)
    .in(jsonBody[User])
    .out(jsonBody[UserEntry])
    .serverLogic { _ => request =>
        (for {
          user <- randomUsersService.createNewUser(request).transact(xa)
        } yield user).toOut
    }

    val endpoints: ServerEndpoints =
      NonEmptyList.of(getUserEndpoint, createUserEndpoint).map(_.tag("randomUsers"))

}
