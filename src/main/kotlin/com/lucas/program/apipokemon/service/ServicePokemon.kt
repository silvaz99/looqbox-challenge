package com.lucas.program.apipokemon.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lucas.program.apipokemon.service.DataClasses.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import org.springframework.boot.configurationprocessor.json.JSONObject
import org.springframework.stereotype.Component

@Component
class ServicePokemon {
     companion object  {
         private val gsonObject : Gson = GsonBuilder().create()

         suspend fun buildResult(query: String, sort: SortType): String? {
             var response = getDataPokemons()
             var listPokemons = getPokemonsByQuery(response, query)

             var listSortedPokemon = SortingService.merge_sort(listPokemons, sort)
             val listPokemonResponseType = PokemonResponseType(listSortedPokemon)

             return gsonObject.toJson(listPokemonResponseType, PokemonResponseType::class.java)
         }

         suspend fun buildResultHighlight(query: String, sort: SortType): String? {
             var response = getDataPokemons()
             var listPokemons = getPokemonsByQueryHighlight(response, query)

             var listSortedPokemon = SortingService.merge_sort(listPokemons, sort)
             var listPokemonResponseHighlightType = PokemonResponseHighlightType(listSortedPokemon)

             return gsonObject.toJson(listPokemonResponseHighlightType, PokemonResponseHighlightType::class.java)
         }

         private suspend fun getDataPokemons() : String {
             val client = HttpClient(CIO)
             val response = client.get("https://pokeapi.co/api/v2/pokemon") {
                 url {
                     parameters.append("query", "0")
                     parameters.append("limit", "1126")
                 }
             }

             return response.body()
         }

         private fun getPokemonsByQuery(response: String, query: String): List<String> {
             val listJson = JSONObject(response).getJSONArray("results").toString()

             val listGsonPokemons = gsonObject.fromJson(listJson , Array<PokemonJson>::class.java)
             return listGsonPokemons.filter {
                 query in it.name
             }.map { it.name }
         }

         private fun getPokemonsByQueryHighlight(response: String, query: String): List<PokemonHighlight> {
             val listJson = JSONObject(response).getJSONArray("results").toString()

             val listGsonPokemons = gsonObject.fromJson(listJson , Array<PokemonJson>::class.java)
             return listGsonPokemons.filter {
                 query in it.name
             }.map { PokemonHighlight(it.name, it.name.replace(query, "<pre>$query</pre>")) }
         }

     }

}