package win.jaxforreal.blockus

import processing.core.PApplet
import java.awt.Color.*

//immediate mode UI
//returns if it is currently pressed
fun PApplet.doButton(
        x: Float,
        y: Float,
        w: Float,
        h: Float,
        text: String,
        normalColor: Int = darkGray.rgb,
        hoverColor: Int = gray.rgb,
        pressColor: Int = lightGray.rgb
): Boolean {
    var isPressed = false
    pushStyle()
    stroke(0f)
    strokeWeight(2f)
    textSize(20f)
    if (win.jaxforreal.blockus.isWithinRect(x, y, w, h, mouseX.toFloat(), mouseY.toFloat())) {
        if (mousePressed) {
            fill(pressColor)
            isPressed = true
        } else {
            fill(hoverColor)
        }
    } else {
        fill(normalColor)
    }
    rect(x, y, w, h)
    fill(0f)
    text(text, (x + w / 2) - textWidth(text) / 2, y + h / 2)
    popStyle()

    return isPressed
}

fun PApplet.doTransparentButton(x: Float, y: Float, w: Float, h: Float): Boolean =
        doButton(x, y, w, h, "", color(white.rgb, 0), color(white.rgb, 50), color(white.rgb, 128))

fun PApplet.doNumberSelect(
        x: Float,
        y: Float,
        w: Float,
        h: Float,
        number: Int,
        range: IntRange = IntRange.EMPTY,
        updatesSinceLastPress: Int
): Pair<Int, Int> {
    text(number, x, y + h)
    if (updatesSinceLastPress > 20) {
        if (doButton(x + w / 2, y, w / 2, h / 2, "+")) return Pair(number + 1, 0)
        if (doButton(x + w / 2, y + h / 2, w / 2, h / 2, "-")) return Pair(number - 1, 0)
    }
    return Pair(number, updatesSinceLastPress + 1)
}

//TODO move to another file, this is not just used for UI
fun isWithinRect(x: Float, y: Float, w: Float, h: Float, testX: Float, testY: Float): Boolean {
    return (testX > x) && (testY > y) && (testX < x + w) && (testY < y + h)
}