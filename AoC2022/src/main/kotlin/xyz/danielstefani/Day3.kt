package xyz.danielstefani

import java.util.stream.IntStream
import kotlin.streams.asSequence

val priorityMap = mutableMapOf<Int, Int>()

fun main() {
    // Prep
    fillMapWithPriorities(97, 122, 1)
    fillMapWithPriorities(65, 90, 27)

    val splitRucksacks = object {}.javaClass
        .classLoader
        .getResource("Day3Input.in")
        ?.readText()
        ?.split("\n")
        ?.map { twoRucksacks ->
            listOf(twoRucksacks.substring(0, twoRucksacks.length / 2).toCharSet(),
                    twoRucksacks.substring(twoRucksacks.length / 2).toCharSet())
        }

    // Part 1
    val splitRucksacksCommonItemsSum = splitRucksacks?.sumOf {
        priorityMap[it[0].intersect(it[1]).elementAt(0)]!!
    }

    // Part 2
    val groupedBy3CommonItemsSum = splitRucksacks
        ?.map { twoRuckSacks -> twoRuckSacks[0].union(twoRuckSacks[1]) }
        ?.chunked(3)
        ?.sumOf { groupOfThree ->
            priorityMap[groupOfThree.reduce { acc, set -> acc.intersect(set) }.elementAt(0)]!! as Int
        }

    println(splitRucksacksCommonItemsSum)
    println(groupedBy3CommonItemsSum)
}

fun fillMapWithPriorities(lowerInclusive: Int, upperInclusive: Int, foldInit: Int) {
    IntStream.range(lowerInclusive, upperInclusive + 1)
        .asSequence()
        .fold(foldInit) { acc, code ->
            priorityMap[code] = acc
            acc + 1
        }
}

fun String.toCharSet(): Set<Int> {
    return this.chars().collect({ mutableSetOf() }, { s, c -> s.add(c) }, { s1, s2 -> s1.addAll(s2) })
}