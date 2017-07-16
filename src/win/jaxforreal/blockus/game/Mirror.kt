package win.jaxforreal.blockus.game

enum class Mirror(val transformation: (Pair<Int, Int>) -> Pair<Int, Int>, val text: String) {
    NORMAL({ (x, y) -> Pair(x, y) }, "none"),
    FLIP_VERT({ (x, y) -> Pair(x, -y) }, "vertical"),
    FLIP_HORIZ({ (x, y) -> Pair(-x, y) }, "horizontal");
    //FLIP_BOTH({ (x, y) -> Pair(-x, -y) });

    fun next(): Mirror = when (this) {
        NORMAL -> FLIP_VERT
        FLIP_VERT -> FLIP_HORIZ
        FLIP_HORIZ -> NORMAL
    //FLIP_BOTH -> NORMAL
    }
}