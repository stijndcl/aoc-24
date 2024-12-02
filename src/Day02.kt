import kotlin.math.abs

fun main() {
    fun isSafe(items: List<Int>): Boolean {
        val zipped = items.zipWithNext()
        return zipped.all { (a, b) -> abs(a - b) in 1..3 } && zipped.map { it.first < it.second }.distinct().size == 1
    }

    fun part1(input: List<String>) = input.count { line ->
        isSafe(line.split(" ").map { it.toInt() })
    }

    fun part2(input: List<String>) = input.count { line ->
        val items = line.split(" ").map { it.toInt() }
        items.indices.any { skippedIndex ->
            isSafe(items.filterIndexed { index, _ -> index != skippedIndex })
        }
    }

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
