package pl.bioinf

import pl.bioinf.data.DNA_STR

fun main() {

    val positiveErrors = 10
    val negativeErrors = 10

//    val dnaSeq = InstGenerator.generateRandomDNASequence(10);
    val dnaSeq  = DNA_STR

    println(dnaSeq)

    val (first, spectrum) = InstGenerator.generateSpectrum(dnaSeq, 3, 0, 0)

    println(first)
    var spec = "["
    spectrum.forEach {
        spec += "$it,"
    }
    spec += "]"
    println(spec)

    val spectrumNodes = InstGenerator.generateInstance(first, spectrum)
//
//    println(spectrumNodes.firstNode)
    println(spectrumNodes.first)
    println(spectrumNodes.nodes)
}