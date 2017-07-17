package win.jaxforreal.blockus.game

import java.awt.Color

enum class Player(val color: Int) {
    RED(Color.red.rgb),
    BLUE(Color.blue.rgb),
    GREEN(Color.green.rgb),
    YELLOW(Color.yellow.rgb);

    fun next(numPlayers: Int): Player {
        val nextIndex = values().indexOf(this) + 1
        return if (nextIndex == numPlayers) values()[0] else values()[nextIndex]
    }
}