package win.jaxforreal.blockus.game

import java.util.*

class BoardCell(var player: Optional<Player> = Optional.empty()) {
    fun isOccupiedBy(otherPlayer: Player): Boolean =
            if (player.isPresent) player.get() == otherPlayer else false
}