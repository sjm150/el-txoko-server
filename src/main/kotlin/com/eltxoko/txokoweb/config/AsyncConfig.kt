package com.eltxoko.txokoweb.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@EnableAsync
@Configuration
class AsyncConfig {

    private val poolSize = 4

    @Bean
    fun mailExecutor(): ThreadPoolTaskExecutor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.corePoolSize = poolSize
        taskExecutor.maxPoolSize = poolSize * 2
        taskExecutor.queueCapacity = poolSize * 5
        taskExecutor.setThreadNamePrefix("MailExecutor-")
        taskExecutor.initialize()
        return taskExecutor
    }
}
