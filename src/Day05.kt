fun main() {
    val mustPrintAfter = mutableMapOf<String, MutableSet<String>>()

    fun isCorrect(sequence: List<String>): Boolean {
        val seen = mutableSetOf<String>()
        return sequence.all { newElement ->
            (mustPrintAfter[newElement]?.intersect(seen)?.isEmpty() ?: true).also { seen.add(newElement) }
        }
    }

    fun fix(sequence: List<String>): List<String> {
        val mutable = mutableListOf(sequence.first())

        sequence.drop(1).forEach { page ->
            var added = false
            run breaking@ {
                mutable.forEachIndexed { index, s ->
                    val set = mustPrintAfter[s]
                    if (set == null || !set.contains(page)) {
                        mutable.add(index, page)
                        added = true
                        return@breaking
                    }
                }
            }

            if (!added) {
                mutable.add(page)
            }
        }

        return mutable
    }

    fun part1(input: List<String>): Int {
        input.takeWhile { it.contains('|') }.forEach { line ->
            val (first, second) = line.split('|')

            if (first !in mustPrintAfter) {
                mustPrintAfter[first] = mutableSetOf()
            }

            mustPrintAfter[first]?.add(second)
        }

        return input.dropWhile { !it.contains(',') }.map { it.split(',') }
            .filter(::isCorrect)
            .sumOf { line -> line[line.size / 2].toInt() }
    }

    fun part2(input: List<String>) = input.dropWhile { !it.contains(',') }.map { it.split(',') }
        .filterNot(::isCorrect)
        .map { fix(it) }
        .sumOf { line -> line[line.size / 2].toInt() }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
