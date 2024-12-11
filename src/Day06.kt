class LoopException : RuntimeException("Loop found")

val directions = listOf(0 to -1, 1 to 0, 0 to 1, -1 to 0)

fun findCaret(grid: List<List<Char>>) = grid.indices.firstNotNullOfOrNull { row ->
    grid[row].indexOf('^').takeIf { it != -1 }?.let { col -> col to row }
}

fun runPathUntilOOB(grid: List<List<Char>>): Set<Pair<Int, Int>> {
    var currentDirection = 0
    var (x, y) = findCaret(grid)!!
    val visitedPositions = mutableSetOf(x to y)
    val visitedWithRotation = mutableSetOf(Triple(x, y, currentDirection))

    while (true) {
        val (fx, fy) = directions[currentDirection]
        val (nx, ny) = (x + fx) to (y + fy)
        val nextTile = grid.getOrNull(ny)?.getOrNull(nx) ?: break

        // Switch direction
        if (nextTile == '#') {
            currentDirection = (currentDirection + 1) % directions.size
        } else {
            if (!visitedWithRotation.add(Triple(nx, ny, currentDirection))) throw LoopException()

            // Add current tile
            x = nx
            y = ny

            visitedPositions += x to y
        }
    }

    return visitedPositions
}

fun main() {

    fun part1(input: List<String>) = runPathUntilOOB(input.map { it.toList() }).size

    fun part2(input: List<String>): Int {
        val grid = input.toMutableList().map { it.toMutableList() }
        return grid.indices.sumOf { row ->
            grid[row].indices.filter { grid[row][it] == '.' }.count { col ->
                try {
                    grid[row][col] = '#'
                    runPathUntilOOB(grid)
                    false
                } catch (e: LoopException) {
                    true
                } finally {
                    grid[row][col] = '.'
                }
            }
        }
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
