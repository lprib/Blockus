package win.jaxforreal.blockus

import processing.event.MouseEvent

interface Screen {
    fun show()
    fun hide()
    fun draw(app: ScreenManagerApplet, delta: Float)
    fun keyPressed(char: Char, code: Int)
    fun mousePressed(event: MouseEvent)
}