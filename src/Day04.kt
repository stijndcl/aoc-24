typealias Coordinate = Pair<Int, Int>

fun List<Int>.pairedPermutations() = sequence {
    forEach { x -> forEach { y -> yield(Pair(x, y)) } }
}

fun main() {
    val factors = listOf(-1, 0, 1)
    val p1coordinates = mapOf('M' to Coordinate(1, 1), 'A' to Coordinate(2, 2), 'S' to Coordinate(3, 3))
    val permutations = factors.pairedPermutations().toList()

    fun countXmasAroundLetter(input: List<String>, pos: Coordinate): Int {
        return permutations.count { (xFactor, yFactor) ->
            p1coordinates.all inner@{ (letter, coordinate) ->
                val (x, y) = coordinate

                val newX = pos.first + x * xFactor
                val newY = pos.second + y * yFactor

                try {
                    letter == input[newY][newX]
                } catch (e: IndexOutOfBoundsException) {
                    false
                }
            }
        }
    }

    fun part1(input: List<String>) = input.indices.sumOf { row ->
        input[row].indices.filter { input[row][it] == 'X' }.sumOf { col ->
            countXmasAroundLetter(input, Coordinate(col, row))
        }
    }

    val p2coordinates = listOf(Pair(Coordinate(-1, -1), Coordinate(1, 1)), Pair(Coordinate(-1, 1), Coordinate(1, -1)))
    val p2expected = listOf('M', 'S')

    fun part2(input: List<String>) = input.indices.sumOf { row ->
        input[row].indices.count { col ->
            input[row][col] == 'A' && p2coordinates.all { (first, second) ->
                val (fx, fy) = first; val (sx, sy) = second

                listOfNotNull(
                    input.getOrNull(row + fx)?.getOrNull(col + fy),
                    input.getOrNull(row + sx)?.getOrNull(col + sy)
                ).sorted() == p2expected
            }
        }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
