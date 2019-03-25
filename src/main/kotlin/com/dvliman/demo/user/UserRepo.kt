package com.dvliman.demo.user

import org.davidmoten.rx.jdbc.Database
import org.davidmoten.rx.jdbc.Parameter
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.publisher.Flux
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import java.sql.ResultSet

@Component
class UserRepo(val db: Database) {

    internal fun toParameters(req: CreateUserRequest): List<Parameter> = listOf(
        Parameter.create("name", req.name),
        Parameter.create("email", req.email))

    internal fun toUser(rs: ResultSet) = User(
        user_id = rs.getInt("user_id"),
        name    = rs.getString("name"),
        email   = rs.getString("email"))

    fun createUser(req: CreateUserRequest): Mono<Int> = db
        .update("INSERT INTO users (name, email) VALUES (:name, :email)")
        .parameters(toParameters(req))
        .returnGeneratedKeys()
        .getAs(Int::class.java)
        .toMono()

    fun fetchUser(req: FetchUserRequest): Mono<User> = db
        .select("SELECT user_id, name, email FROM users WHERE user_id = ${req.user_id}")
        .get { rs -> toUser(rs) }
        .toMono()

    fun allUsers(): Flux<User> = db
        .select("SELECT user_id, name, email FROM users")
        .get { rs -> toUser(rs) }
        .toFlux()
}