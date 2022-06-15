package com.lucas.program.apipokemon.application

import com.lucas.program.apipokemon.service.ServicePokemon
import com.lucas.program.apipokemon.service.SortType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class EntrypointAPI {

    @GetMapping("/pokemons")
    suspend fun getPokemons(@Valid @RequestParam query: String, @RequestParam(required = false) sort: String?): ResponseEntity<String> {
        val sortType = if (sort.equals("Length", true)) SortType.LENGTH else SortType.ALPHABETICAL;
        return ResponseEntity.ok(ServicePokemon.buildResult(query!!, sortType))
    }

    @GetMapping("/pokemons/highlight")
    suspend fun getPokemonsHighlight(@Valid @RequestParam query: String, @RequestParam(required = false) sort: String?): ResponseEntity<String> {
        val sortType = if (sort.equals("Length", true)) SortType.LENGTH else SortType.ALPHABETICAL;
        return ResponseEntity.ok(ServicePokemon.buildResultHighlight(query!!, sortType))
    }

}