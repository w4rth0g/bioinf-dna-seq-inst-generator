package pl.bioinf

import pl.bioinf.data.Node
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
                addPositiveErrors(spectrum, positiveErrors, kNum).toTypedArray()
            } else {
                spectrum
            }

            var spectrumNodes = spectrumWithErrors.map {
                Node(it)
            }
            val firstNode = Node(first)
            spectrumNodes = spectrumNodes.plus(firstNode)

            spectrumNodes.forEach { node ->
                spectrumNodes.asSequence()
                    .filter {
                        it.id !== node.id && it.id !== firstNode.id
                    }
                    .forEach { nextNode ->
                        val pokrycieD = node.checkMaxPokrycie(nextNode)
                        if (pokrycieD != 0) {
                            node.nexts[nextNode.id] = pokrycieD
                        }
                    }
            }

            handleDuplicateErrors(spectrumNodes)

            return NodesList(firstNode, spectrumNodes)
        }

        fun generateRandomDNASequence(length: Int): String {
            val nucleotides = listOf('A', 'C', 'G', 'T')
            return (1..length).map { nucleotides.random() }.joinToString("")
        }

        private fun handleDuplicateErrors(spectrumNodes: List<Node>) {
            spectrumNodes.forEach { node ->
                node.nexts.asSequence()
                    .map {spectrumNodes.getById(it.key)}
                    .filter { nnode: Node -> node.value == nnode.value }
                    .forEach {nnode: Node ->
                        val nnodesSeq = nnode.nexts.asSequence()
                            .map {spectrumNodes.getById(it.key)}
                            .filter { nnnode: Node -> nnode.value == nnnode.value }

                        if (nnodesSeq.count() > 0) {
                            nnodesSeq.forEach { nnnode: Node ->
                                node.nexts[nnode.id] = 3
                                nnode.nexts[nnnode.id] = 2
                            }
                        } else {
                            node.nexts[nnode.id] = 2
                        }
                    }
            }
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