package win.jaxforreal.blockus

import processing.event.MouseEvent
import win.jaxforreal.blockus.game.Piece
import win.jaxforreal.blockus.game.PieceData

fun main(args: Array<String>) {
    processing.core.PApplet.main("win.jaxforreal.blockus.Main")
}

class Main : ScreenManagerApplet() {
    private var currentScreenInternal: Screen? = null

    override var currentScreen: Screen
        get() = currentScreenInternal!!
        set(value) {
            currentScreenInternal?.hide()
            currentScreenInternal = value
            currentScreenInternal?.show()
        }

    override fun settings() {
        size(500, 500)
    }

    override fun setup() {
        surface.setTitle("Blockus")
        surface.setResizable(true)
        currentScreen = MainMenuScreen()

        for (block in PieceData.values()) {
            println(Piece(block))
        }
    }

    override fun draw() {
        currentScreen.draw(this, 0.1f)
    }

    override fun exit() {
        currentScreen.hide()
        super.exit()
    }

    override fun keyPressed() {
        currentScreen.keyPressed(key, keyCode)
    }

    override fun mousePressed(event: MouseEvent) {
        currentScreen.mousePressed(event)
    }
}