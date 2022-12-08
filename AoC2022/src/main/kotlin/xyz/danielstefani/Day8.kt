package xyz.danielstefani

import java.util.function.Function

lateinit var trees: List<List<Int>>
const val GRID_SIZE = 4

fun main() {
    trees = object {}.javaClass
        .classLoader
        .getResource("Day8Input.in")!!
        .readText()
        .split("\n")
        .map { it.map { it.digitToInt() }.toList() }


    // Part 1
    var visible = 0
    for (row in trees.indices) {
        for (column in trees.indices) {
            val top = isVisible(trees[row][column], row - 1, column, rowFunction = { r -> r - 1 })
            val bottom = isVisible(trees[row][column], row + 1, column, rowFunction = { r -> r + 1 })
            val left = isVisible(trees[row][column], row, column - 1, columnFunction = { c -> c - 1 })
            val right = isVisible(trees[row][column], row, column + 1, columnFunction = { c -> c + 1 })
            if (top || bottom || left || right) visible++
        }
    }
    println(visible)

    // Part 2
    var maxScenicScore = 0
    for (row in trees.indices) {
        for (column in trees.indices) {
            val top = getScenicScore(row - 1, column, rowFunction = { r -> r - 1 })
            val bottom = getScenicScore(row + 1, column, rowFunction = { r -> r + 1 })
            val left = getScenicScore(row, column - 1, columnFunction = { c -> c - 1 })
            val right = getScenicScore(row, column + 1, columnFunction = { c -> c + 1 })
            val score = top * bottom * left * right
            if (score > maxScenicScore) maxScenicScore = score
        }
    }
    println(maxScenicScore)
}

fun isVisible(treeHeight: Int, row: Int, column: Int,
              rowFunction: Function<Int, Int> = Function { r -> r },
              columnFunction: Function<Int, Int> = Function { c -> c }
): Boolean {
    if (column < 0 || column > GRID_SIZE || row < 0 || row > GRID_SIZE) return true
    if (treeHeight <= trees[row][column]) return false
    if (column == 0 || column == GRID_SIZE || row == 0 || row == GRID_SIZE) return true
    return isVisible(treeHeight, rowFunction.apply(row), columnFunction.apply(column), rowFunction, columnFunction)
}

fun getScenicScore(row: Int, column: Int,
              rowFunction: Function<Int, Int> = Function { r -> r },
              columnFunction: Function<Int, Int> = Function { c -> c }
): Int {
    if (column <= 0 || column >= GRID_SIZE || row <= 0 || row >= GRID_SIZE) return 0
    return 1 + getScenicScore(rowFunction.apply(row), columnFunction.apply(column), rowFunction, columnFunction)
}