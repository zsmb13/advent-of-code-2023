package day03

import readInput
import kotlin.math.abs

data class Cell(val x: Int, val y: Int)

fun Cell.neighbours(other: Cell) =
        abs(x - other.x) <= 1 && abs(y - other.y) <= 1

sealed class Entity(val cells: List<Cell>)

fun Entity.neighbours(other: Entity) =
        cells.any { cell -> other.cells.any { cell.neighbours(it) } }

fun Entity.neighboursAny(others: List<Entity>) =
        others.any { it.neighbours(this) }

class Symbol(x: Int, y: Int, val value: Char) : Entity(listOf(Cell(x, y)))

class Number(x: Int, y: Int, val value: String)
    : Entity(List(value.length) { i -> Cell(x + i, y) })

val Number.intValue get() = value.toInt()

fun main() {
    fun parse(input: List<String>): Pair<List<Number>, List<Symbol>> {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()

        input.forEachIndexed { y, line ->
            var currentNumber = ""

            line.forEachIndexed { x, char ->
                when (char) {
                    '.' -> Unit
                    in '0'..'9' -> currentNumber += char
                    else -> symbols += (Symbol(x, y, char))
                }
                if (char !in '0'..'9' && currentNumber.isNotEmpty()) {
                    numbers += (Number(x - currentNumber.length, y, currentNumber))
                    currentNumber = ""
                }
            }

            if (currentNumber.isNotEmpty()) {
                numbers += (Number(line.length - currentNumber.length, y, currentNumber))
            }
        }
        return Pair(numbers, symbols)
    }

    fun part1(input: List<String>): Int {
        val (numbers, symbols) = parse(input)

        return numbers
                .filter { number -> number.neighboursAny(symbols) }
                .sumOf { it.intValue }
    }

    fun part2(input: List<String>): Int {
        val (numbers, symbols) = parse(input)

        val gearRatios = symbols
            .filter { it.value == '*' }
            .map { symbol ->
                val neighbours = numbers.filter { it.neighbours(symbol) }
                if (neighbours.size == 2) {
                    val (a, b) = neighbours
                    a.intValue * b.intValue
                } else {
                    0
                }
            }

        return gearRatios.sum()
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
