package pl.bioinf

import pl.bioinf.data.DNA_STR

fun main() {

    val positiveErrors = 10
    val negativeErrors = 10

    val dnaSeq = InstGenerator.generateRandomDNASequence(500);

    println(dnaSeq)

    val spectrumNodes = InstGenerator.generateInstance(dnaSeq, 8, positiveErrors, negativeErrors)

    println(spectrumNodes)
}