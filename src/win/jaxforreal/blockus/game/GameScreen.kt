package win.jaxforreal.blockus.game

import processing.event.MouseEvent
import win.jaxforreal.blockus.Assets
import win.jaxforreal.blockus.Screen
import win.jaxforreal.blockus.ScreenManagerApplet

class GameScreen(val numPlayers: Int) : Screen {
    private val board = Board(20, 20, 400f, 400f, 20f, 20f)
    private var currentPlayer = Player.RED

    private val pieceSelectors: Map<Player, PieceSelect> = mapOf(
            Player.RED to PieceSelect(110f, 10f, 5f),
            Player.GREEN to PieceSelect(110f, 10f, 5f),
            Player.BLUE to PieceSelect(110f, 10f, 5f),
            Player.YELLOW to PieceSelect(110f, 10f, 5f)
    )

    private val pieceSelect get() = pieceSelectors[currentPlayer]!!
    private val currentPiece get() = pieceSelect.selectedPiece

    override fun show() {}

    override fun hide() {}

    override fun draw(app: ScreenManagerApplet, delta: Float) = with(app) {
        image(Assets.background, 0f, 0f, width.toFloat(), height.toFloat())

        //stretch board with window resize
        board.heightPixels = app.height - 140f
        board.widthPixels = board.heightPixels
        board.draw(app)

        //piece select is snapped to right of screen
        pieceSelect.size = height / 100.toFloat()
        pieceSelect.x = app.width - pieceSelect.size * 6
        pieceSelect.draw(app)

        //draw preview of piece to place
        if (board.isWithinBoard(mouseX.toFloat(), mouseY.toFloat())) {
            val (cx, cy) = board.screenToCellCoords(mouseX.toFloat(), mouseY.toFloat()).get()
            val (dx, dy) = board.cellToScreenCoords(cx, cy)
            //low alpha for overlay
            currentPiece.draw(app, dx, dy, board.cellWidth, color(currentPlayer.color, 100))
            noCursor()
        } else {
            cursor()
        }

        //rotation and mirror HUD
        text("Rotation: ${currentPiece.rotation.text}", 5f, height.toFloat() - 5)
        val mirrorText = "Mirror: ${currentPiece.mirror.text}"
        text(mirrorText, width - textWidth(mirrorText) - 5, height.toFloat() - 5)
    }

    override fun keyPressed(char: Char, code: Int) {
        if (char == 'r') {
            currentPiece.rotation = currentPiece.rotation.next()
        }
        if (char == 't') {
            currentPiece.mirror = currentPiece.mirror.next()
        }
        if (char == 'j') {
            pieceSelect.highlightNext()
        }
    }

    override fun mousePressed(event: MouseEvent) {
        val mouseX = event.x.toFloat()
        val mouseY = event.y.toFloat()

        //place piece if available on mousePress
        if (board.isWithinBoard(mouseX, mouseY)) {
            val (cellX, cellY) = board.screenToCellCoords(mouseX, mouseY).get()

            //only place piece at mouse location if is is available
            if (board.canPlacePiece(cellX, cellY, currentPiece, currentPlayer)) {
                board.placePiece(cellX, cellY, currentPiece, currentPlayer)
                //mark piece as used, so it can't be used again
                pieceSelect.setPieceUsed(currentPiece.data)
                //next player's turn
                currentPlayer = currentPlayer.next(numPlayers)
            }
        }
    }
}