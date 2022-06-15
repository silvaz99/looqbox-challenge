package com.lucas.program.apipokemon.service

import com.lucas.program.apipokemon.service.DataClasses.*

class SortingService {
    companion object {
        fun <T : Comparable<T>> merge_sort(itens: List<T>, sort : SortType): List<T>{
            if (itens.size <= 1) {
                return itens
            }

            val middle = itens.size / 2
            val left = itens.subList(0, middle)
            val right = itens.subList(middle, itens.size)

            return merge(merge_sort(left, sort), merge_sort(right, sort), sort)
        }

        fun <T: Comparable<T>> merge(left: List<T>, right: List<T>, sort : SortType) : List<T> {
            var indexRight = 0
            var indexLeft = 0
            val resultList : MutableList<T> = mutableListOf()

            while (indexLeft < left.count() && indexRight < right.count()) {
                val resultComparasion : Boolean = if(sort == SortType.ALPHABETICAL) {
                    left[indexLeft] <= right[indexRight]
                } else {
                    when (left[indexLeft]) {
                        is String -> compareStrings(left[indexLeft] as String, right[indexRight] as String)
                        is PokemonHighlight -> comparePokemonStrings(
                            left[indexLeft] as PokemonHighlight,
                            right[indexRight] as PokemonHighlight
                        )
                        else -> false
                    }
                }

                if(resultComparasion) {
                    resultList.add(left[indexLeft])
                    indexLeft++
                } else {
                    resultList.add(right[indexRight])
                    indexRight++
                }
            }

            if (indexLeft < left.size) {
                resultList.addAll(left.subList(indexLeft, left.size))
            }

            if (indexRight < right.size) {
                resultList.addAll(right.subList(indexRight, right.size))
            }

            return resultList;
        }

        private fun <T : String> compareStrings(t: T, t1: T): Boolean {
            return t.length < t1.length
        }

        private fun <T : PokemonHighlight> comparePokemonStrings(t: T, t1: T): Boolean {
            return t.name.length < t1.name.length
        }
    }
}

