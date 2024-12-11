fun main() {
    val mustPrintAfter = mutableMapOf<String, MutableSet<String>>()

    fun isCorrect(sequence: List<String>): Boolean {
        val seen = mutableSetOf<String>()
        return sequence.all { newElement ->
            (mustPrintAfter[newElement]?.intersect(seen)?.isEmpty() ?: true).also { seen += newElement }
        }
    }

    fun fix(sequence: List<String>): List<String> {
        val mutable = mutableListOf(sequence.first())

        sequence.drop(1).forEach { page ->
            mutable.indexOfFirst { s -> mustPrintAfter[s]?.contains(page) != true }.let { i ->
                if (i == -1) mutable += page else mutable.add(i, page)
            }
        }

        return mutable
    }

    fun part1(input: List<String>): Int {
        input.takeWhile { it.contains('|') }.forEach { line ->
            val (first, second) = line.split('|')
            mustPrintAfter.getOrPut(first) { mutableSetOf() } += second
        }

        return input.dropWhile { !it.contains(',') }
            .map { it.split(',') }
            .filter(::isCorrect)
            .sumOf { it[it.size / 2].toInt() }
    }

    fun part2(input: List<String>) = input.dropWhile { !it.contains(',') }
        .map { it.split(',') }
        .filterNot(::isCorrect)
        .map { fix(it) }
        .sumOf { it[it.size / 2].toInt() }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
