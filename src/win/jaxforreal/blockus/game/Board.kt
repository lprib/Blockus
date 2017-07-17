package win.jaxforreal.blockus.game

import processing.core.PApplet
import win.jaxforreal.blockus.isWithinRect
import java.awt.Color
import java.util.*

class Board(
        val widthCells: Int,
        val heightCells: Int,
        var widthPixels: Float,
        var heightPixels: Float,
        var drawX: Float,
        val drawY: Float) {

    private val cells: Array<Array<BoardCell>> = Array(widthCells, { Array(heightCells, { BoardCell() }) })
    val cellWidth get() = widthPixels / widthCells
    val cellHeight get() = heightPixels / heightCells

    //checks if the piece can be placed at the cell coordinates by player
    fun canPlacePiece(cellX: Int, cellY: Int, piece: Piece, player: Player): Boolean {
        //isDiagonalToSelf will accumulate for each square of the piece
        //this means if any square of the piece is diagonal to self, the piece will be valid
        var isDiagonalToSelf = false

        for ((coordX, coordY) in piece.coords) {
            if (!isCellAvailable(cellX + coordX, cellY + coordY, player))
                return false
            if (isCellDiagonalToPlayer(cellX + coordX, cellY + coordY, player)) isDiagonalToSelf = true
        }
        return isDiagonalToSelf
    }


    //places piece, regardless of the legality of the move
    fun placePiece(cellX: Int, cellY: Int, piece: Piece, player: Player) {
        for ((coordX, coordY) in piece.coords) {
            setCellPlayer(cellX + coordX, cellY + coordY, player)
        }
    }

    //checks if the cell is available for the player to place on
    //(is within bounds, isn't taken by another player, isn't touching the players own cells)
    private fun isCellAvailable(x: Int, y: Int, player: Player): Boolean {
        return isCellWithinBounds(x, y) && !cells[x][y].player.isPresent && !isTouchingPlayer(x, y, player)
    }

    //checks whether the cell is diagonal to specified player
    private fun isCellDiagonalToPlayer(x: Int, y: Int, player: Player): Boolean {
        return testOccupiedAndBounds(x + 1, y + 1, player) ||
                testOccupiedAndBounds(x - 1, y + 1, player) ||
                testOccupiedAndBounds(x + 1, y - 1, player) ||
                testOccupiedAndBounds(x - 1, y - 1, player) ||
        //corners return true (for starting locations)
                (x == 0 && y == 0) ||
                (x == 0 && y == heightCells - 1) ||
                (x == widthCells - 1 && y == 0) ||
                (x == widthCells - 1 && y == heightCells - 1)
    }

    //checks whether the cell at (cellX, cellY) is within bounds of map
    private fun isCellWithinBounds(cellX: Int, cellY: Int): Boolean = (cellX >= 0) && (cellY >= 0) && (cellX < widthCells) && (cellY < heightCells)

    //draw board at location and scale
    //TODO never stretched, so only one dimension is needed for width/height
    fun draw(app: PApplet) = with(app) {
        pushStyle()
        stroke(0f)
        strokeWeight(1f)
        for (cellX in 0..widthCells - 1) {
            for (cellY in 0..heightCells - 1) {
                if (cells[cellX][cellY].player.isPresent) {
                    fill(cells[cellX][cellY].player.get().color)
                } else {
                    fill(Color.darkGray.rgb)
                }
                val (x, y) = cellToScreenCoords(cellX, cellY)
                rect(x, y, cellWidth, cellHeight)
            }
        }
        popStyle()
    }

    //get the cell under the screen location
    //may be optional.empty if not on map
    fun screenToCellCoords(x: Float, y: Float): Optional<Pair<Int, Int>> {
        if (isWithinBoard(x, y)) {
            return Optional.of(Pair(
                    Math.floor((x - drawX) / cellWidth.toDouble()).toInt(),
                    Math.floor((y - drawY) / cellHeight.toDouble()).toInt()))
        } else {
            return Optional.empty()
        }
    }

    fun cellToScreenCoords(cellX: Int, cellY: Int): Pair<Float, Float>
            = Pair(cellX * cellWidth + drawX, cellY * cellHeight + drawY)

    //checks if screen coords (x, y) are within the board
    fun isWithinBoard(x: Float, y: Float) = isWithinRect(drawX, drawY, widthPixels, heightPixels, x, y)

    //set cell at (cellX, cellY) to be owned by player
    fun setCellPlayer(cellX: Int, cellY: Int, player: Player) {
        cells[cellX][cellY].player = Optional.of(player)
    }

    //checks if cell is touching any call owned by specified player
    fun isTouchingPlayer(cellX: Int, cellY: Int, player: Player): Boolean {
        return testOccupiedAndBounds(cellX, cellY, player) ||
                testOccupiedAndBounds(cellX + 1, cellY, player) ||
                testOccupiedAndBounds(cellX, cellY + 1, player) ||
                testOccupiedAndBounds(cellX - 1, cellY, player) ||
                testOccupiedAndBounds(cellX, cellY - 1, player)
    }

    //test if cell is occupied by player, also does bound check
    private fun testOccupiedAndBounds(cellX: Int, cellY: Int, player: Player): Boolean {
        if (isCellWithinBounds(cellX, cellY)) {
            return cells[cellX][cellY].isOccupiedBy(player)
        } else return false
    }
}