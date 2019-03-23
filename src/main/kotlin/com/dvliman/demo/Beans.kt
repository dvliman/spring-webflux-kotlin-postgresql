package com.dvliman.demo

import org.davidmoten.rx.jdbc.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Beans {

    @Bean
    fun providesDatabaseConn(config: Config): Database
        = Database.from(config.dbUrl!!, Runtime.getRuntime().availableProcessors() * 2)
}