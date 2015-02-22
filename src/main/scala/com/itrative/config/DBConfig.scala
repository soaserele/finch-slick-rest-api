package com.itrative.config

import com.typesafe.config.ConfigFactory

import scala.slick.jdbc.JdbcBackend.Database

trait DBConfig {
  val config = ConfigFactory.load()
  val profile = config.getConfig("db").getConfig(config.getString("db.profile"))

  val db = Database.forURL(
    url = profile.getString("url"),
    user = profile.getString("username"),
    password = profile.getString("password"),
    driver = profile.getString("driver")
  )
}
