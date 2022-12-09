package xyz.danielstefani

import java.util.function.Consumer
import java.util.function.Function
import kotlin.math.abs

fun main() {
    val movements = object {}.javaClass
        .classLoader
        .getResource("Day9Input.in")!!
        .readText()
        .split("\n")

    val uniquePositionSet = mutableSetOf<Pair<Int, Int>>()
    val head = TracerPoint(); val tail = TracerPoint()
    println("Head -> ${head.current()}, Tail -> ${tail.current()}")

    for (movement in movements) {
        var direction: String
        var magnitude: Int
        movement.split(" ").apply {
            direction = this[0]
            magnitude = this[1].toInt()
        }

        for (i in 0 until magnitude) {
            when (direction) {
                "U" -> head.move(modifyY = { it + 1 })
                "D" -> head.move(modifyY = { it - 1 })
                "L" -> head.move(modifyX = { it - 1 })
                "R" -> head.move(modifyX = { it + 1 })
            }
            tail.queue(head.current()) // Update tail position
        }
    }

    uniquePositionSet.add(tail.current())
    tail.trace { uniquePositionSet.add(it) }
    print(uniquePositionSet.size)
}

class TracerPoint(
    private var x: Int = 0,
    private var y: Int = 0
) {
    private val parentMoves = ArrayDeque<Pair<Int, Int>>()

    fun move(modifyX: Function<Int, Int> = Function { it }, modifyY: Function<Int, Int> = Function { it }) {
        x = modifyX.apply(x)
        y = modifyY.apply(y)
    }

    fun queue(parentCurrent: Pair<Int, Int>) {
        parentMoves.add(parentCurrent)
    }

    fun current(): Pair<Int, Int> { return Pair(x, y) }

    fun trace(positionConsumer: Consumer<Pair<Int, Int>>) {
        for (next in parentMoves) {
            val current = current()

            println("Head -> ${next}, Tail -> ${current}")

            // Remove overlaps / diagonal movements
            val shouldNotMove =
                abs(current.first + current.second - next.first + next.second) == 1
            if (shouldNotMove) { continue }

            this.x = next.first
            this.y = next.second
            positionConsumer.accept(next)
        }
    }
}