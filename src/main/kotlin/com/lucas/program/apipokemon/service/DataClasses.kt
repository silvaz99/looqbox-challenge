package com.lucas.program.apipokemon.service

import com.google.gson.annotations.SerializedName

class DataClasses {
    data class PokemonJson(
        @SerializedName("name") val name: String,
        @SerializedName("url") val url: String
    )

    class PokemonHighlight(name: String, highlight: String) : Comparable<PokemonHighlight> {
        @SerializedName("name") val name: String = name
        @SerializedName("highlight") val highlight: String = highlight

        override fun compareTo(other: PokemonHighlight): Int {
            return this.name.compareTo(other.name)
        }
    }

    data class PokemonResponseType(
        @SerializedName("results") val results : List<String>
    )

    data class PokemonResponseHighlightType(
        @SerializedName("results") val results : List<PokemonHighlight>
    )
}