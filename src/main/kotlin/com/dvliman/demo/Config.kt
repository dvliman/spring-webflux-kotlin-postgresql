package com.dvliman.demo;

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Config {

    @Value("\${demo.db.url}")
    val dbUrl: String? = null
}