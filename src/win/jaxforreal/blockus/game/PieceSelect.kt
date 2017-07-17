package win.jaxforreal.blockus.game

import processing.core.PApplet
import win.jaxforreal.blockus.doTransparentButton
import java.awt.Color

class PieceSelect(var x: Float, var y: Float, var size: Float = 40f) {
    var selectedPiece = Piece(PieceData.P1A)
    var usedPieces = ArrayList<PieceData>()

    fun draw(app: PApplet) = with(app) {
        PieceData.values().forEachIndexed { iter, currentData ->
            //        for (iter in 0..PieceData.values().size - 1) {

            val drawColor = if (currentData == selectedPiece.data)
                Color.yellow.rgb
            else if (usedPieces.contains(currentData))
                Color.gray.rgb
            else
                Color.red.rgb

            Piece(PieceData.values()[iter]).draw(app, x, y + iter * size * 4, size, drawColor)

            if (!usedPieces.contains(currentData) &&
                    app.doTransparentButton(x, y + iter * size * 4, size * 4, size * 4)) {
                selectedPiece = Piece(PieceData.values()[iter])
            }
        }
    }

    //sets the given piece as already used, and moves the selectedPiece to the next available one
    fun setPieceUsed(piece: PieceData) {
        usedPieces.add(piece)

        selectedPiece = Piece(getNextAvailablePiece(piece))
    }

    private fun getNextAvailablePiece(piece: PieceData): PieceData {
        var nextPieceIndex = PieceData.values().indexOf(piece) + 1
        if (nextPieceIndex == PieceData.values().size) nextPieceIndex = 0

        while (usedPieces.contains(PieceData.values()[nextPieceIndex])) {
            nextPieceIndex++
            if (nextPieceIndex == PieceData.values().size)
                nextPieceIndex = 0
        }

        return PieceData.values()[nextPieceIndex]
    }

    private fun firstAvailablePiece(): PieceData =
            PieceData.values().filter { !usedPieces.contains(it) }.first()

    fun highlightNext() {
        selectedPiece = Piece(getNextAvailablePiece(selectedPiece.data))
    }
}