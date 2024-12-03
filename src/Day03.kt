fun main() {
    val p1re = Regex("""mul\((\d{1,3}),(\d{1,3})\)""")

    fun part1(input: List<String>) = p1re.findAll(input.joinToString("")).sumOf { match ->
        match.groupValues.drop(1).map { it.toInt() }.let { (a, b) -> a * b }
    }

    val p2re = Regex("""(mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\))""")
    var enabled = true

    fun part2(input: List<String>) = p2re.findAll(input.joinToString("")).sumOf { match ->
        when (match.value) {
            "do()" -> 0.also { enabled = true }
            "don't()" -> 0.also { enabled = false }
            else -> if (enabled) match.groupValues.drop(2).map { it.toInt() }.let { (a, b) -> a * b } else 0
        }
    }

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
