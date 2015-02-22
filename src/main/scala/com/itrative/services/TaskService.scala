package com.itrative.services

import com.itrative.config.{Mapper, DBConfig}
import com.itrative.model.{Task, Tasks}
import com.twitter.finagle.Service
import io.finch.request._
import io.finch.response._
import io.finch.route._
import io.finch.{AnyOps, HttpRequest, HttpResponse}
import io.finch.jackson._

import scala.slick.driver.MySQLDriver.simple._

object TaskService extends Mapper with DBConfig {
  val taskReader = RequiredBody.as[Task]

  case class GetTasks() extends Service[HttpRequest, HttpResponse] {
    def apply(req: HttpRequest) =
      Ok(
        db.withSession {
          implicit session: Session =>
            Tasks.q.list
        }
      ).toFuture
  }

  case class GetTask(id: Long) extends Service[HttpRequest, HttpResponse] {
    def apply(req: HttpRequest) =
      Ok(
        db.withSession {
          implicit session: Session =>
            Tasks.q.filter(_.id === Option(id)).list.headOption
        }
      ).toFuture
  }

  case class DeleteTask(id: Long) extends Service[HttpRequest, HttpResponse] {
    def apply(req: HttpRequest) = {
      db.withSession {
        implicit session: Session =>
          Tasks.q.filter(_.id === Option(id)).delete
      }
      Ok().toFuture
    }
  }

  case class CreateTask() extends Service[HttpRequest, HttpResponse] {
    def apply(req: HttpRequest) =
      for {
        task <- taskReader(req)
      } yield {
        db.withSession {
          implicit session: Session =>
            val autoinc = (Tasks.q returning Tasks.q.map(_.id)) += task
            Ok(Tasks.q.filter(_.id === autoinc).list.headOption)
        }
      }
  }

  val route: Endpoint[HttpRequest, HttpResponse] =
    (Get / "tasks" / long /> GetTask) |
      (Delete / "tasks" / long /> DeleteTask) |
      (Get / "tasks" /> GetTasks()) |
      (Post / "tasks" /> CreateTask())
}