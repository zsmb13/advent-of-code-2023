import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

fun <E> List<E>.split(separator: E): List<List<E>> {
    val result = mutableListOf<List<E>>()
    val sublist = mutableListOf<E>()
    for (element in this) {
        if (element == separator) {
            result += sublist.toList()
            sublist.clear()
        } else {
            sublist += element
        }
    }
    if (sublist.isNotEmpty()) {
        result += sublist.toList()
    }
    return result
}
