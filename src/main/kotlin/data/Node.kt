package pl.bioinf.data

import pl.bioinf.substrCompare
import java.util.*

data class Node(val value : String, val id: UUID = UUID.randomUUID(), var nexts:  MutableMap<UUID, Int> = mutableMapOf())

fun Node.checkMaxPokrycie(node: Node): Int {
    val pokrycie = this.value.substrCompare(node.value)
    if (this.value.length - pokrycie <= 3) {
        return this.value.length - pokrycie
    }
    return 0
}