package models

case class User(email: Email, password: String, salt: String)
