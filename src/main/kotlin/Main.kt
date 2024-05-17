package pl.bioinf

import pl.bioinf.data.DNA_STR

fun main() {

    val positiveErrors = 10
    val negativeErrors = 10

    val spectrumNodes = InstGenerator.generateInstance(DNA_STR, 8, positiveErrors, negativeErrors)

    println(spectrumNodes)
}