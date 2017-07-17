package win.jaxforreal.blockus

import processing.event.MouseEvent
import win.jaxforreal.blockus.game.GameScreen


class MainMenuScreen() : Screen {

    override fun mousePressed(event: MouseEvent) {
    }

    override fun keyPressed(char: Char, code: Int) {
    }

    override fun show() {}

    override fun hide() {}

    override fun draw(app: ScreenManagerApplet, delta: Float) {
        app.image(Assets.background, 0f, 0f, app.width.toFloat(), app.height.toFloat())
        if (app.doButton(
                10f,
                10f,
                app.width / 3 - 15f,
                80f,
                "2 Player",
                app.color(38, 160, 69),
                app.color(78, 200, 109)
        )) {
            app.currentScreen = GameScreen(2)
        }
        if (app.doButton(
                app.width / 3 + 5f,
                10f,
                app.width / 3 - 10f,
                80f,
                "3 Player",
                app.color(54, 165, 141),
                app.color(7, 221, 203)
        )) {
            app.currentScreen = GameScreen(3)
        }
        if (app.doButton(
                2 * app.width / 3f + 5f,
                10f,
                app.width / 3 - 15f,
                80f, "4 Player",
                app.color(56, 120, 175),
                app.color(96, 160, 225)
        )) {
            app.currentScreen = GameScreen(4)
        }

        if (app.doButton(
                10f,
                100f,
                app.width - 20f,
                80f,
                "Quit",
                app.color(112, 26, 26),
                app.color(165, 66, 66)
        )) {
            app.exit()
        }
    }
}