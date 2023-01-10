package com.softwaremill.bootzooka.randomusers

import cats.implicits._
import com.softwaremill.bootzooka.infrastructure.Doobie._

class RandomUserModel {

  def inset(user: UserEntry): ConnectionIO[Unit] = {
    sql"""INSERT INTO  random_user (id, first_name, email, password, birth_day)
         | VALUES (${user.id}, ${user.firstName}, ${user.email}, ${user.password}, ${user.birthDay})""".stripMargin.update.run.void
  }

  def getAll(): ConnectionIO[List[UserEntry]] =
    sql"""SELECT id, first_name, email, password, birth_day from random_user""".query[UserEntry].to[List]

  def insertMany(ps: List[UserEntry]): ConnectionIO[Int] = {
    val sql = "INSERT into random_user (id, first_name, email, password, birth_day) values (?, ?, ?, ?, ?)"
    Update[UserEntry](sql).updateMany(ps)
  }

}

case class UserEntry(
id: String,
firstName: String,
email: String,
password: String,
birthDay: String
)
