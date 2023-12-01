fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val first = line.first { it.isDigit() }
            val last = line.last { it.isDigit() }
            (first - '0') * 10 + (last - '0')
        }
    }

    val digits = listOf(
            "some-value-that-does-not-occur-in-the-input",
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
    )

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            fun getDigitValue(index: Int): Int {
                val spelled = line.substring(index, index + 2)
                return digits.indexOfFirst { it.startsWith(spelled) }
            }

            val firstNumericalDigit = line.indexOfFirst { it.isDigit() }
            val firstSpelledOutDigit = line.indexOfAny(digits)

            val first = if (firstSpelledOutDigit == -1 || (firstNumericalDigit != -1 && firstNumericalDigit < firstSpelledOutDigit)) {
                line[firstNumericalDigit] - '0'
            } else {
                getDigitValue(firstSpelledOutDigit)
            }

            val lastNumericalDigit = line.indexOfLast { it.isDigit() }
            val lastSpelledOutDigit = line.lastIndexOfAny(digits)

            val last = if ((lastSpelledOutDigit == -1) || (lastNumericalDigit != -1 && lastNumericalDigit > lastSpelledOutDigit)) {
                line[lastNumericalDigit] - '0'
            } else {
                val spelled = line.substring(lastSpelledOutDigit, lastSpelledOutDigit + 2)
                getDigitValue(lastSpelledOutDigit)
            }

            first * 10 + last
        }
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
