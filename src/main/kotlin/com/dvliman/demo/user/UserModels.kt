package com.dvliman.demo.user

import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.http.MediaType.APPLICATION_JSON
import reactor.core.publisher.Mono

data class User(
    val user_id: Int,
    val name   : String,
    val email  : String
)

data class CreateUserRequest (
    val name : String,
    val email: String
)

data class FetchUserRequest (
    val user_id: Int
)

typealias CreateUserResponse = FetchUserRequest

fun toServerResponse(resp: CreateUserResponse) = ServerResponse
    .ok()
    .contentType(APPLICATION_JSON)
    .body(Mono.just(resp), CreateUserResponse::class.java)

fun toServerResponse(user: User) = ServerResponse
    .ok()
    .contentType(APPLICATION_JSON)
    .body(Mono.just(user), User::class.java)