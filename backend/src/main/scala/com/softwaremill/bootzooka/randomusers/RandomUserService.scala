package com.softwaremill.bootzooka.randomusers

import com.softwaremill.bootzooka.infrastructure.Doobie.ConnectionIO

import java.util.UUID

case class RandomUserService(randomUserClient: RandomUserClient, randomUserModel: RandomUserModel) {

  private val user2Entry: User => UserEntry = (user: User) => UserEntry(
    id = UUID.randomUUID().toString,
    firstName = user.firstName,
    email = user.email,
    password = user.password,
    birthDay = user.birthDay
  )

  def createNewUser(user: User): ConnectionIO[UserEntry] = {
    val userEntry = user2Entry(user)
    for {
      _ <- randomUserModel.inset(userEntry)
    } yield userEntry
  }

  def pullNewUsers(): ConnectionIO[Int] = {
    val list = randomUserClient.getRandomUsers()
      .map(_.persons.map(User.apply))
      .getOrElse(List.empty)
      .map(user2Entry)
    randomUserModel.insertMany(list)
  }


  def getUsers(): ConnectionIO[List[UserEntry]] = {
    randomUserModel.getAll()
  }

}

