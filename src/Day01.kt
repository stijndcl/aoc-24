import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()

        input.forEach { line ->
            val (l, r) = line.split("   ")
            left.add(l.toInt())
            right.add(r.toInt())
        }

        left.sort()
        right.sort()

        return left.zip(right).sumOf { abs(it.first - it.second) }
    }

    fun part2(input: List<String>): Int {
        val left = mutableListOf<Int>()
        val right = mutableMapOf<Int, Int>().withDefault { 0 }
        input.forEach { line ->
            val (l, r) = line.split("   ")
            left.add(l.toInt())
            right[r.toInt()] = (right[r.toInt()] ?: 0) + 1
        }

        return left.sumOf { it * right.getOrDefault(it, 0) }
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
