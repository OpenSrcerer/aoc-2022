package xyz.danielstefani

import kotlin.math.abs

val shapeToScore = mapOf(
    Pair("A", 1), // Rock
    Pair("B", 2), // Paper
    Pair("C", 3), // Scissors
    Pair("X", 1), // Rock
    Pair("Y", 2), // Paper
    Pair("Z", 3)  // Scissors
)

fun main(args: Array<String>) {
    // Prep
    val rounds = object {}.javaClass
        .classLoader
        .getResource("Day2Input.in")
        ?.readText()
        ?.split("\n")
        ?.map {
            it.split(", ")[0].split(" ")
                .map { letter -> shapeToScore[letter]!! }
        }

    // Part 1
    val scoreFirstAssumption = rounds?.sumOf { extractScoreFromRound(it) }

    // Part 2
    val scoreSecondAssumption = rounds?.map { convertToShapeToPlay(it) }
        ?.sumOf { extractScoreFromRound(it) }

    println(scoreFirstAssumption)
    println(scoreSecondAssumption)
}

fun extractScoreFromRound(elfMyChoices: List<Int>): Int {
    val minChoiceDelta = abs(elfMyChoices[0] - elfMyChoices[1]) <= 1
    val roundOutcome =
        if (elfMyChoices[0] > elfMyChoices[1] && minChoiceDelta) 0
        else if (elfMyChoices[0] < elfMyChoices[1] && !minChoiceDelta) 0
        else if (elfMyChoices[0] == elfMyChoices[1]) 3
        else 6
    return elfMyChoices[1] + roundOutcome
}

fun convertToShapeToPlay(elfEndChoices: List<Int>): List<Int> {
    return when (elfEndChoices[1]) {
        1 -> listOf(
            elfEndChoices[0],
            if (elfEndChoices[0] == 1) 3 else elfEndChoices[0] - 1
        )
        2 -> listOf(elfEndChoices[0], elfEndChoices[0])
        3 -> listOf(
            elfEndChoices[0],
            if (elfEndChoices[0] == 3) 1 else elfEndChoices[0] + 1
        )
        else -> emptyList() // won't happen
    }
}