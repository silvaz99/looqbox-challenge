package com.lucas.program.apipokemon

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApiPokemonApplication

fun main(args: Array<String>) {
	runApplication<ApiPokemonApplication>(*args)
}
