package xyz.danielstefani

fun main(args: Array<String>) {
    // Prep
    val elvesCaloriesSummed = object {}.javaClass
        .classLoader
        .getResource("Day1Input.in")
        ?.readText()
        ?.split("\n\n")
        ?.map { it.split("\n").map { s -> s.toInt() } }
        ?.reduce { acc, ints -> acc + ints.sum() }

    // Part 1
    val mostCaloriesFirst = elvesCaloriesSummed?.max()

    // Part 2
    val mostCaloriesTopThreeSummed = elvesCaloriesSummed?.sortedDescending()
        ?.subList(0, 3)
        ?.sum()

    println("Most calories #1: $mostCaloriesFirst")
    println("Most calories top 3 sum: $mostCaloriesTopThreeSummed")
}