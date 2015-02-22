package com.itrative.model

import scala.slick.driver.MySQLDriver.simple._

case class Task(
                 id: Option[Long],
                 name: String,
                 description: Option[String],
                 done: Option[Boolean]
                 )

class Tasks(tag: Tag) extends Table[Task](tag, "tasks") {
  def id = column[Option[Long]]("ID", O.PrimaryKey, O.AutoInc)

  def name = column[String]("NAME", O.NotNull)

  def description = column[Option[String]]("DESCRIPTION", O.Nullable)

  def done = column[Option[Boolean]]("DONE", O.Nullable)

  def * = (id, name, description, done) <>(Task.tupled, Task.unapply _)
}

object Tasks {
  val q = TableQuery[Tasks]
}
