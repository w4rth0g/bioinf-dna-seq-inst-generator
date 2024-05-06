package pl.bioinf

import pl.bioinf.data.Node
import java.util.*

fun List<Node>.getById(id: UUID): Node {
    return this.asSequence().filter { it.id == id }.first();
}

fun String.splitBy(amount: Int): Array<String> {
    return this.windowed(size = amount, step = 1, partialWindows = true).toTypedArray()
}

fun String.substrCompare(str: String): Int {
    if (this.length != str.length) return -1

    var sbstrLen = this.length - 1
    while (sbstrLen > 1) {
        if (this.substring(0..<sbstrLen) == str.substring(this.length - sbstrLen..<this.length) ||
            this.substring(this.length - sbstrLen..<this.length) == str.substring(0..sbstrLen)
        ) {
            return sbstrLen
        }

        sbstrLen--
    }

    return -1
}