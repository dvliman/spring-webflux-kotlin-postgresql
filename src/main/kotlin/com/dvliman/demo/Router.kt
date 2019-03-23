package com.dvliman.demo

import com.dvliman.demo.user.UserHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.router

@Component
class Router {

    @Bean
    fun route(userHandler: UserHandler) = router {
        contentType(APPLICATION_JSON).nest {
            POST("/api/users/create", userHandler::createUser)
            POST("/api/users/fetch", userHandler::fetchUser)
            POST("/api/users/all", userHandler::allUsers)
        }
    }
}
