package xyz.danielstefani

import java.util.stream.IntStream
import kotlin.streams.asSequence

fun main() {
    val part1part2 = object {}.javaClass
        .classLoader
        .getResource("Day4Input.in")
        ?.readText()
        ?.split("\n")
        ?.map {
            val l = it.split(",").map { it.split("-").map { it.toInt() } }
            val p = Pair(
                IntStream.range(l[0][0], l[0][1] + 1).asSequence().toSet(),
                IntStream.range(l[1][0], l[1][1] + 1).asSequence().toSet()
            )
            Pair(
                p.first.containsAll(p.second) || p.second.containsAll(p.first),
                p.first.intersect(p.second).isNotEmpty()
            )
        }
        ?.fold(mutableListOf(0, 0)) { a, p -> if (p.first) a[0]++; if (p.second) a[1]++; a }

    print(part1part2)
}