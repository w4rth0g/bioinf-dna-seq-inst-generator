package pl.bioinf

import pl.bioinf.data.DNA_STR

fun main() {

    val positiveErrors = 10
    val negativeErrors = 10

//    val dnaSeq = InstGenerator.generateRandomDNASequence(10);
    val dnaSeq  = DNA_STR

    println(dnaSeq)

//    println("------ " + "TCAACATAC".substrCompare("AACATACAG"))

    val spectrumNodes = InstGenerator.generateInstance(dnaSeq, 3, 0, 0)
//
//    println(spectrumNodes.firstNode)
    println(spectrumNodes.first)
    println(spectrumNodes.nodes.filter { it.value.second > 1 })
}