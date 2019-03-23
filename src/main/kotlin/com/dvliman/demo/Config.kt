package com.dvliman.demo;

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Value("\${demo.db.url}")
    val dbUrl: String? = null
}