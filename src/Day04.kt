typealias Coordinate = Pair<Int, Int>

fun List<Int>.pairedPermutations() = sequence {
    forEach { x ->
        forEach { y ->
            yield(Pair(x, y))
        }
    }
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

    fun part2(input: List<String>) = input.indices.sumOf { row ->
        input[row].indices.filter { input[row][it] == 'A' }.count { col ->
            p2coordinates.all { (first, second) ->
                try {
                    val firstLetter = input[row + first.first][col + first.second]
                    val secondLetter = input[row + second.first][col + second.second]
                    firstLetter == 'S' && secondLetter == 'M' || firstLetter == 'M' && secondLetter == 'S'
                } catch (e: IndexOutOfBoundsException) {
                    false
                }
            }
        }
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
