import kotlin.math.abs

fun main() {
    fun isSafe(items: List<Int>): Boolean {
        val increasing = items[0] < items[1]
        for (i in 1 until items.size) {
            val newIncreasing = items[i] > items[i - 1]
            if (newIncreasing != increasing) {
                return false
            }

            val diff = abs(items[i] - items[i - 1])
            if (1 > diff || 3 < diff) {
                return false
            }
        }

        return true
    }

    fun part1(input: List<String>): Int {
        return input.count { line ->
            isSafe(line.split(" ").map { it.toInt() })
        }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val items = line.split(" ").map { it.toInt() }
            items.indices.any { skippedIndex ->
                val newList = items.toMutableList()
                newList.removeAt(skippedIndex)
                isSafe(newList)
            }
        }
    }

    // Read the input from the `src/Day02.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
