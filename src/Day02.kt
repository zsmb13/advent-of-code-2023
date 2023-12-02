import kotlin.math.max

fun main() {
    data class Cubes(val red: Int, val green: Int, val blue: Int)

    data class Game(val id: Int, val draws: List<Cubes>)

    fun parse(str: String): Game {
        val (_id, _draws) = str.split(": ")
        val id = _id.drop(5).toInt()

        operator fun List<String>.get(color: String) =
                find { it.endsWith(" $color") }?.dropLast(color.length + 1)?.toInt() ?: 0

        val draws = _draws.split("; ").map { draw ->
            val pieces = draw.split(", ")
            Cubes(pieces["red"], pieces["green"], pieces["blue"])
        }
        return Game(id, draws)
    }

    fun Cubes.isPossibleFrom(supply: Cubes) =
            red <= supply.red && green <= supply.green && blue <= supply.blue

    fun part1(input: List<String>): Int {
        val supply = Cubes(red = 12, green = 13, blue = 14)
        return input.sumOf { line ->
            val (id, draws) = parse(line)
            if (draws.all { draw -> draw.isPossibleFrom(supply) }) id else 0
        }
    }

    fun max(a: Cubes, b: Cubes) =
            Cubes(max(a.red, b.red), max(a.green, b.green), max(a.blue, b.blue))

    fun Cubes.power() = red * green * blue

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (_, draws) = parse(line)
            val set = draws.fold(Cubes(0, 0, 0)) { acc, draw -> max(acc, draw) }
            set.power()
        }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
