fun main() {
    data class Mapping(
            val inputRange: LongRange,
            val offset: Long,
    ) {
        fun map(value: Long): Long? = when (value) {
            in inputRange -> value + offset
            else -> null
        }
    }

    fun String.toLongs() = split(" ").map(String::toLong)

    fun String.toMapping(): Mapping {
        val (destStart, sourceStart, length) = this.toLongs()
        return Mapping(sourceStart..<sourceStart + length, destStart - sourceStart)
    }

    fun part1(input: List<String>): Long {
        val inputs = input.split("")

        val seeds = inputs[0][0].substringAfter("seeds: ").toLongs()

        val mappings = inputs.drop(1).map { it.drop(1).map { it.toMapping() } }
        val locations = seeds.map { seed ->
            mappings.fold(seed) { thing, mapping ->
                mapping.firstNotNullOfOrNull { it.map(thing) } ?: thing
            }
        }

        return locations.min()
    }

    fun part2(input: List<String>): Long {
        val inputs = input.split("")

        val seeds = inputs[0][0]
                .removePrefix("seeds: ")
                .toLongs()
                .chunked(2)
                .map { (first, length) -> first..<first + length }
        val mappings = inputs.drop(1).map { it.drop(1).map { it.toMapping() } }

        return seeds.minOf { range ->
            range.minOfOrNull {
                mappings.fold(it) { current, mapping ->
                    mapping.firstNotNullOfOrNull { it.map(current) } ?: current
                }
            } ?: Long.MAX_VALUE
        }
    }

    val input = readInput("Day05_sample")
//    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
