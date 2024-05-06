package pl.bioinf

import pl.bioinf.data.Node
import pl.bioinf.data.checkMaxPokrycie

class InstGenerator {

    companion object {

        fun generateInstance(nodeStrSeq: String, kNum: Int): List<Node> {
            val spectrum = nodeStrSeq.splitBy(kNum)

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

            return spectrumNodes
        }
    }
}