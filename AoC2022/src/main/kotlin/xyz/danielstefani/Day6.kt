package xyz.danielstefani

val charSet = mutableSetOf<Char>()

fun main() {
    val transmission = object {}.javaClass
        .classLoader
        .getResource("Day6Input.in")
        ?.readText()
        ?.toList()

    // Part 1
    findFirstGroupWithNDistinctChars(transmission, 4)

    // Part 2
    findFirstGroupWithNDistinctChars(transmission, 14)
}

fun findFirstGroupWithNDistinctChars(transmission: List<Char>?, distinctCharacters: Int) {
    for (index in distinctCharacters - 1 until transmission?.size!!) {
        charSet.apply {
            this.clear()
            this.addAll(transmission.subList(index - (distinctCharacters - 1), index + 1))
        }
        if (charSet.size == distinctCharacters) {
            println(index + 1)
            break
        }
    }
}