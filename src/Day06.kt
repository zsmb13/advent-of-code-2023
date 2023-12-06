import java.math.BigInteger

fun main() {
    fun part1(input: List<String>): Int {
        val times = input[0].removePrefix("Time:").trim().split(WHITESPACE).map { it.toInt() }
        val distances = input[1].removePrefix("Distance:").trim().split(WHITESPACE).map { it.toInt() }

        return times.foldIndexed(1) { index, res, time ->
            val dist = distances[index]
            res * (1..time).count { n ->
                (time - n) * n > dist
            }
        }
    }

    fun part2(input: List<String>): Int {
        val time = input[0].removePrefix("Time:").replace(WHITESPACE, "").toLong()
        val distance = input[1].removePrefix("Distance:").replace(WHITESPACE, "").toLong()

        return (1..time).count { n ->
            (time - n) * n > distance
        }
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
