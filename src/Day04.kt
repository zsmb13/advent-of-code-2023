import kotlin.math.pow

fun main() {
    fun countWinners(game: String): Int {
        val (winningCard, ourCard) = game.substringAfter(": ").split(" | ")
        val winningNumbers = winningCard.windowed(2, 3)
        val ourNumbers = ourCard.windowed(2, 3)
        return ourNumbers.count { it in winningNumbers }
    }
    
    fun Int.pow(n: Int) = (1..n).fold(1) { acc, _ -> acc * this }

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            when (val count = countWinners(line)) {
                0 -> 0
                else -> 2.pow(count - 1)
            }
        }
    }

    fun part2(input: List<String>): Int {
        val cardCounts = IntArray(input.size) { 1 }
        input.forEachIndexed { index, game ->
            repeat(countWinners(game)) { i ->
                cardCounts[index + 1 + i] += cardCounts[index]
            }
        }
        return cardCounts.sum()
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
