package win.jaxforreal.blockus.game

import processing.core.PApplet
import java.awt.Color

//piece object holds its shape (data, immutable)
//and rotation (mutable)
class Piece(val data: PieceData) {
    var rotation: Rotation = Rotation.DEGREE_0
    var mirror: Mirror = Mirror.NORMAL

    val coords: Array<Pair<Int, Int>>
        get() = data.coords.map(rotation.transformation).map(mirror.transformation)
                .map { (x, y) ->
                    val (minX, minY) = getMinCoords()
                    Pair(x - minX, y - minY)
                }.toTypedArray()

    //draw shape at location/size
    fun draw(app: PApplet, x: Float, y: Float, cellSize: Float,
             color: Int = Color.red.rgb)

            = with(app) {
        pushStyle()
        strokeWeight(1f)
        stroke(0f)
        fill(color)
        for ((cellX, cellY) in coords) {
            rect(x + cellX * cellSize, y + cellY * cellSize, cellSize, cellSize)
        }
        popStyle()
    }

    //cache the toString value, because it is O(n)
    private val generateString: String = {
        val lines = arrayOf(charSeq(), charSeq(), charSeq(), charSeq(), charSeq(), charSeq())
        for (coord in data.coords) {
            lines[coord.second][coord.first] = '#'
        }

        val output = StringBuilder()
        for (line in lines)
            output.appendln(line.joinToString(""))

        output.toString()
    }()

    //used for toString
    private fun charSeq(): Array<Char> = Array(6, { '0' })

    override fun toString(): String = generateString

    //TODO stuff like this needs caching because its O(n)
    private fun getMinCoords(): Pair<Int, Int> {
        var minX = 0
        var minY = 0

        for ((x, y) in data.coords.map(rotation.transformation).map(mirror.transformation)) {
            if (x < minX) minX = x
            if (y < minY) minY = y
        }
        return Pair(minX, minY)
    }
}

enum class PieceData(vararg initCoords: Int) {
    P1A(0, 0),
    P2A(0, 0, 1, 0),
    P3A(0, 0, 1, 0, 1, 1),
    P3B(0, 0, 1, 0, 2, 0),
    P4A(0, 0, 1, 0, 0, 1, 1, 1),
    P4B(1, 0, 0, 1, 1, 1, 2, 1),
    P4C(0, 0, 1, 0, 2, 0, 3, 0),
    P4D(2, 0, 0, 1, 1, 1, 2, 1),
    P4E(1, 0, 2, 0, 0, 1, 1, 1),
    P5A(0, 0, 0, 1, 1, 1, 2, 1, 3, 1),
    P5B(1, 0, 1, 1, 0, 2, 1, 2, 2, 2),
    P5C(0, 0, 0, 1, 0, 2, 1, 2, 2, 2),
    P5D(1, 0, 2, 0, 3, 0, 0, 1, 1, 1),
    P5E(0, 0, 1, 0, 2, 0, 3, 0, 4, 0),
    P5F(0, 0, 0, 1, 1, 1, 0, 2, 1, 2),
    P5G(1, 0, 2, 0, 0, 1, 1, 1, 0, 2),
    P5H(0, 0, 1, 0, 0, 1, 0, 2, 1, 2),
    P5I(1, 0, 2, 0, 0, 1, 1, 1, 1, 2),
    P5J(1, 0, 0, 1, 1, 1, 2, 1, 1, 2),
    P5K(1, 0, 0, 1, 1, 1, 2, 1, 3, 1);


    //converts the varargs of constructor into pairs of coordinates
    val coords: Array<Pair<Int, Int>> = {
        val pairs: ArrayList<Pair<Int, Int>> = ArrayList()
        for (i in 0..initCoords.size / 2 - 1) {
            pairs.add(Pair(initCoords[i * 2], initCoords[i * 2 + 1]))
        }

        pairs.toTypedArray()
    }()
}