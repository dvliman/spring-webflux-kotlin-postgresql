package com.dvliman.demo.user

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.switchIfEmpty

@RestController
class UserHandler(val userRepo: UserRepo) {

    fun createUser(req: ServerRequest): Mono<ServerResponse> = req
        .bodyToMono(CreateUserRequest::class.java)
        .flatMap(userRepo::createUser)
        .map { userId -> CreateUserResponse(user_id = userId)}
        .flatMap(::toServerResponse)
        .onErrorResume { e -> badRequest("users-create-failed", e.message) }

    fun fetchUser(req: ServerRequest): Mono<ServerResponse> = req
        .bodyToMono(FetchUserRequest::class.java)
        .flatMap(userRepo::fetchUser)
        .flatMap(::toServerResponse)
        .switchIfEmpty { notFound("users-fetch-not-found", "users-fetch-not-found") }
        .onErrorResume { e -> badRequest("users-fetch-failed", e.message) }

    fun allUsers(req: ServerRequest): Mono<ServerResponse> = ServerResponse
        .ok()
        .body(userRepo.allUsers(), User::class.java)

    data class Error        (val code: String, val msg: String)
    data class ErrorResponse(val error: Error)

    internal fun badRequest(code: String, msg: String?) =
        errorResponse(HttpStatus.BAD_REQUEST, code, msg)

    internal fun notFound(code: String, msg: String?) =
        errorResponse(HttpStatus.NOT_FOUND, code, msg)

    internal fun errorResponse(status: HttpStatus, code: String, msg: String?) =
        ServerResponse.status(status)
            .contentType(APPLICATION_JSON)
            .body(Mono.just(ErrorResponse(Error(code, msg ?: code))),
                  ErrorResponse::class.java)
}
