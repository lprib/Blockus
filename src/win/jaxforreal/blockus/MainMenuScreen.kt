package win.jaxforreal.blockus

import processing.core.PApplet
import processing.event.MouseEvent
import win.jaxforreal.blockus.game.GameScreen


class MainMenuScreen(app: PApplet) : Screen {
    val bg = app.loadImage("background.png")

    override fun mousePressed(event: MouseEvent) {
    }

    override fun keyPressed(char: Char, code: Int) {
    }

    override fun show() {}

    override fun hide() {}

    override fun draw(app: ScreenManagerApplet, delta: Float) {
        app.image(bg, 0f, 0f, app.width.toFloat(), app.height.toFloat())
        if (app.doButton(10f, 10f, app.width / 3 - 15f, 80f, "2 Player")) {
            app.currentScreen = GameScreen(2)
        }
        if (app.doButton(app.width / 3 + 5f, 10f, app.width / 3 - 10f, 80f, "3 Player")) {
            app.currentScreen = GameScreen(3)
        }
        if (app.doButton(2 * app.width / 3f + 5f, 10f, app.width / 3 - 15f, 80f, "4 Player")) {
            app.currentScreen = GameScreen(4)
        }

        if (app.doButton(10f, 100f, app.width - 20f, 80f, "Quit")) {
            app.exit()
        }
    }
}