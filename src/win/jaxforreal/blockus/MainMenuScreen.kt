package win.jaxforreal.blockus

import processing.event.MouseEvent
import win.jaxforreal.blockus.game.GameScreen


class MainMenuScreen : Screen {
    override fun mousePressed(event: MouseEvent) {
    }

    override fun keyPressed(char: Char, code: Int) {
    }

    override fun show() {
        println("show")
    }

    override fun hide() {
        println("hide")
    }

    override fun draw(app: ScreenManagerApplet, delta: Float) {
        app.background(0f)
        if (app.doButton(10f, 10f, app.width - 20f, 80f, "Start Game")) {
            app.currentScreen = GameScreen()
        }
        if (app.doButton(10f, 100f, app.width - 20f, 80f, "Quit")) {
            app.exit()
        }
    }
}