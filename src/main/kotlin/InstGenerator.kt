package pl.bioinf

import pl.bioinf.data.NodesList
import pl.bioinf.data.checkMaxPokrycie
import kotlin.random.Random

class InstGenerator {

    companion object {

        fun generateInstance(nodeStrSeq: String, kNum: Int, positiveErrors: Int?, negativeErrors: Int?): NodesList {
            var spectrum = nodeStrSeq.splitBy(kNum)

            val first = spectrum.first()

            spectrum = spectrum.drop(1).toTypedArray()
            spectrum.sort()

            // Dodaj błędy
            var spectrumWithErrors: Array<String>

            spectrumWithErrors = if (negativeErrors != null) {
                addNegativeErrors(spectrum, negativeErrors).toTypedArray()
            } else {
                spectrum
            }

            spectrumWithErrors = if (positiveErrors != null) {
                addPositiveErrors(spectrumWithErrors, positiveErrors, kNum).toTypedArray()
            } else {
                spectrum
            }

            val duplicates = (spectrumWithErrors + first).groupingBy { it }.eachCount().filter { it.value > 1 }

            val spectrumNodes = mutableMapOf<String, Pair<MutableMap<String, Int>, Int>>()
            spectrumNodes[first] = mutableMapOf<String, Int>() to 1
            val nodesList = NodesList(first, (spectrumNodes + spectrumWithErrors.associateWith { mutableMapOf<String, Int>() to 1 }).toMutableMap())

            nodesList.nodes.forEach { node ->
                nodesList.nodes.asSequence()
                    .filter {
                        it.key != node.key && it.key != nodesList.nodes.keys.first()
                    }
                    .forEach { nextNode ->
                        val pokrycieD = checkMaxPokrycie(node.key, nextNode.key)
                        if (pokrycieD != 0) {
                            node.value.first[nextNode.key] = pokrycieD
                        }
                    }
            }

            duplicates.forEach { dupl ->
                val sec = nodesList.nodes[dupl.key]!!.second
                nodesList.nodes[dupl.key] = nodesList.nodes[dupl.key]!!.copy(second = sec + dupl.value - 1)
            }

            return nodesList
        }

        fun generateRandomDNASequence(length: Int): String {
            val nucleotides = listOf('A', 'C', 'G', 'T')
            return (1..length).map { nucleotides.random() }.joinToString("")
        }

        private fun addNegativeErrors(spectrum: Array<String>, numErrors: Int): List<String> {
            val mutableSpectrum = spectrum.toMutableList()

            repeat(numErrors) {
                if (mutableSpectrum.isNotEmpty()) {
                    mutableSpectrum.removeAt(Random.nextInt(mutableSpectrum.size))
                }
            }

            return mutableSpectrum
        }

        private fun addPositiveErrors(spectrum: Array<String>, numErrors: Int, kNum: Int): List<String> {
            val mutableSpectrum = spectrum.toMutableList()

            repeat(numErrors) {
                val randomOligonucleotide = generateRandomOligonucleotide(kNum)
                mutableSpectrum.add(randomOligonucleotide)
            }

            return mutableSpectrum
        }

        private fun generateRandomOligonucleotide(length: Int): String {
            val nucleotides = listOf('A', 'C', 'G', 'T')
            return (1..length).map { nucleotides.random() }.joinToString("")
        }
    }
}