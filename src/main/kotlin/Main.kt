package pl.bioinf

import java.util.*

const val DNA_STR = "ATACCTAGTGTAGTCCGGACGCTTTCTGAGAAGTTTTCATCCTACGCCGATCCCAGCTGAGCGAGGGCATACCTGATCCACATCCGTTCTTATAGACTGAAATCTCGAACTTTCTCATGCCGGCTCAGTACTTGCTTTGCCAAGGTTACCTTTGCTTAGCTCGCCAGCAGCGGGTCCGGGATTCCTAGATTCGAAATTGAGAAACAATTACCGCTCGTGCCCGACAACAATTTGGCAACGTCAGAGATGTGGTAGGATGGACCATCATTGCGACGGGTCAACTAAACCCCACGAAGCCTGCGGTCCCACTTAGGCCAGGAGTGCCCTCCCCTACTTATATAGGATTGGCAGCAAACATTGAGCAATGACCATTCCTGAGATCATTCCAGCTCTGTTCAGAGCAGTCAACCTATATCACGAACGCATGATGTCGAGTATGCATAGGAGGGTATGTGGAGCGAGGCGCTTCCGTGAGCCGGATTATCACATAGTCCTACGATTTGGTGAGTATTACCACCGACTATACAGCCGTCAATAACACTTCGAGGGTGATGTCCTCCTCACGTGCGCTCTGTCTCATGACATTGTACGCACAGGGTG";

//const val DNA_STR = "CTTTTGCCGTACACAGCCACGCTGTTAAGTCTACTCATTTGCCAATAAGG"

data class Node(val value : String, val id: UUID = UUID.randomUUID(), var nexts:  MutableMap<UUID, Int> = mutableMapOf())

fun Node.checkMaxPokrycie(node: Node): Int {
    val pokrycie = this.value.substrCompare(node.value)
    if (this.value.length - pokrycie <= 3) {
        return this.value.length - pokrycie
    }
    return 0
}

fun main() {
    val spectrum = DNA_STR.splitBy(5)

    spectrum.sort()

    val spectrumNodes = spectrum.map {
        Node(it)
    }

    spectrumNodes.forEach { node ->
        spectrumNodes.asSequence()
            .filter { it.id !== node.id && !node.nexts.contains(it.id) && !it.nexts.contains(node.id) }
            .forEach { nextNode ->
                val pokrycieD = node.checkMaxPokrycie(nextNode)
                if (pokrycieD != 0) {
                    node.nexts[nextNode.id] = pokrycieD
                }
            }
    }

    // handle errors
    spectrumNodes.forEach { node ->
        node.nexts.asSequence()
            .map {spectrumNodes.getById(it.key)}
            .filter { node.value == it.value }
            .forEach {nnode ->
                val nnodesSeq = nnode.nexts.asSequence()
                    .map {spectrumNodes.getById(it.key)}
                    .filter { nnode.value == it.value }

                if (nnodesSeq.count() > 0) {
                    nnodesSeq.forEach { nnnode ->
                        node.nexts[nnode.id] = 3
                        nnode.nexts[nnnode.id] = 2
                    }
                } else {
                    node.nexts[nnode.id] = 2
                }
            }
    }

    println(spectrumNodes)
}

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