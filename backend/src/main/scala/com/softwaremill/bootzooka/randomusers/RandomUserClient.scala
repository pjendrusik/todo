package com.softwaremill.bootzooka.randomusers

import io.circe.{Decoder, HCursor}
import sttp.client3._
import sttp.client3.circe._
import io.circe.generic.auto._
import io.circe.syntax._

case class Person(firstName: String, lastName: String, email: String, password: String, birthDate: String)
case class RandomUserResponse(persons: List[Person])

class RandomUserClient() {

  implicit val decodePerson: Decoder[Person] = new Decoder[Person] {
    final def apply(c: HCursor): Decoder.Result[Person] = {
      val name = c.downField("name")
      val login = c.downField("login")
      val dob = c.downField("dob")

      for {
        firstName <- name.downField("first").as[String]
        lastName <- name.downField("last").as[String]
        email <- c.downField("email").as[String]
        password <- login.downField("password").as[String]
        birthDate <- dob.downField("date").as[String]
      } yield Person(firstName, lastName, email, password, birthDate)
    }
  }

  implicit val decodeResult: Decoder[RandomUserResponse] = new Decoder[RandomUserResponse] {
    override def apply(c: HCursor): Decoder.Result[RandomUserResponse] = {
      for {
        persons <- c.downField("results").as[List[Person]]
      } yield RandomUserResponse(persons)
    }
  }

  def getRandomUsers(): Option[RandomUserResponse] = {

    val request = basicRequest.get(uri"https://randomuser.me/api/?results=10")
    val backend = HttpClientSyncBackend()

    val response: Identity[Response[Either[ResponseException[String, io.circe.Error], RandomUserResponse]]] =
      request
        .response(asJson[RandomUserResponse])
        .send(backend)

    response.body.toOption
  }

}
