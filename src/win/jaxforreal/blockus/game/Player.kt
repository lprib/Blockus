package win.jaxforreal.blockus.game

import java.awt.Color

enum class Player(val color: Int) {
    RED(Color.red.rgb),
    BLUE(Color.blue.rgb),
    GREEN(Color.green.rgb),
    YELLOW(Color.yellow.rgb);

    fun next() = when (this) {
        RED -> BLUE
        BLUE -> GREEN
        GREEN -> YELLOW
        YELLOW -> RED
    }
}