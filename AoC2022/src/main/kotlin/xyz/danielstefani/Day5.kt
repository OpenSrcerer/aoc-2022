package xyz.danielstefani

import java.util.Stack
import java.util.stream.IntStream

// Create the stacks to use for the logic
val stacks: List<Stack<Char>> = IntStream.rangeClosed(1, 9).mapToObj { Stack<Char>() }.toList()

fun main() {
    val stackAndInstructions = object {}.javaClass
        .classLoader
        .getResource("Day5Input.in")
        ?.readText()
        ?.split("\n\n")

    // Read Stack
    readStack(stackAndInstructions)

    // Execute Instructions
    stackAndInstructions?.get(1)
        ?.split("\n")
        ?.map { it.split(" ") }
        ?.forEach { instruction ->
            println(instruction)
            IntStream.range(0, instruction[1].toInt()).forEach {
                stacks[instruction[5].toInt() - 1].add(stacks[instruction[3].toInt() - 1].pop())
            }
        }

    // Print Message
    print(stacks.map { it.lastElement() })
}

fun readStack(stackAndInstructions: List<String>?) {
    // Read the serialized stack
    val serializedStack = stackAndInstructions?.get(0)
        ?.split("\n")
        ?.map { it.toCharArray() }
        ?.reversed()
    serializedStack?.forEach { it.forEachIndexed { index, c ->
        if (c.isLetter()) {
            stacks[serializedStack[0][index].digitToInt() - 1].push(c)
        }
    } }
}