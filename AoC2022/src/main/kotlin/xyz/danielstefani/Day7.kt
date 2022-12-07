package xyz.danielstefani

import java.util.*

fun main() {
    val commands = object {}.javaClass
        .classLoader
        .getResource("Day7Input.in")
        ?.readText()
        ?.split("\n")

    var rootDir = Directory("/")
    var currentWorkingDir = rootDir
    for (line in commands!!) {
        // Handle directory changes
        if (line.startsWith("$ cd")) {
            if (line.substring(5) == "/") { // Only first case: Create root dir
                rootDir = Directory("/")
                currentWorkingDir = rootDir
            } else if (line.substring(5) == "..") { // Go up one directory
                currentWorkingDir = currentWorkingDir.parent!!
            } else { // Switch to an already created directory
                currentWorkingDir = currentWorkingDir[line.substring(5)]
            }
        }

        // Handle directory creation (create all as subDirs of CWD)
        if (line.startsWith("dir")) {
            val toAdd = Directory(line.substring(4))
            toAdd.parent = currentWorkingDir
            currentWorkingDir.children[toAdd.name] = toAdd
        }

        // Handle file creation
        if (line[0].isDigit()) {
            currentWorkingDir.files.add(line.split(" ")[0].toLong())
        }
    }

    // Part 1
    println(rootDir.getTotalSum())

    // Part 2
    println(rootDir.findSmallestChildClosestToButGreaterThan(30000000 - (70000000 - rootDir.size())))
}

class Directory(
    val name: String,
    val children: MutableMap<String, Directory> =  mutableMapOf(),
    val files: MutableList<Long> = mutableListOf(),
    var parent: Directory? = null
) {
    operator fun get(s: String): Directory {
        return children[s]!!
    }

    fun size(): Long {
        return files.sum() + children.values.sumOf { it.size() }
    }

    fun getTotalSum(): Long {
        val currSize = this.size()
        val childrenSize = children.values.sumOf { it.getTotalSum() }
        return if (currSize <= 100_000) currSize + childrenSize
               else childrenSize
    }

    fun findSmallestChildClosestToButGreaterThan(condition: Long): Long {
        var max = Long.MAX_VALUE
        val stack = Stack<Directory>() .apply {
            push(this@Directory)
        }
        val items = mutableListOf<Directory>()

        while (stack.isNotEmpty()) {
            val current = stack.pop()
            val currentSize = current.size()
            if (currentSize in condition until max) {
                max = currentSize
            }
            items.add(current)
            current.children.values.forEach { stack.push(it) }
        }

        return max
    }
}

