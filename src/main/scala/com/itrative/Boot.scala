package com.itrative

import com.itrative.services.TaskService
import com.twitter.finagle.Httpx
import com.twitter.util.Await

object Boot extends App {

  Await.ready(Httpx.serve(":8081", TaskService.route))
}