package com.eltxoko.txokoweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class TxokoWebApplication

fun main(args: Array<String>) {
	runApplication<TxokoWebApplication>(*args)
}
