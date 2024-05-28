package pl.bioinf.data

import pl.bioinf.substrCompare
import java.util.*

class NodesList (val first: String, val nodes: MutableMap<String, Pair<MutableMap<String, Int>, Int>>)

fun checkMaxPokrycie(node1: String, node2: String): Int {
    val pokrycie = node1.substrCompare(node2)
    if (node1.length - pokrycie <= 3) {
        return node1.length - pokrycie
    }
    return 0
}