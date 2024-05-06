package pl.bioinf

import pl.bioinf.data.DNA_STR

fun main() {

    val spectrumNodes = InstGenerator.generateInstance(DNA_STR, 8)

    println(spectrumNodes)
}