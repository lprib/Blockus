package win.jaxforreal.blockus.game

//transformation rotates a point the given amount around the origin
enum class Rotation(val transformation: (Pair<Int, Int>) -> Pair<Int, Int>, val text: String) {
    DEGREE_0({ it }, "none"),
    DEGREE_90({ (x, y) -> Pair(y, -x) }, "90 degrees"),
    DEGREE_180({ (x, y) -> Pair(-x, -y)}, "180 degrees"),
    DEGREE_270({ (x, y) -> Pair(-y, x)}, "270 degrees");

    fun next(): Rotation = when(this) {
        DEGREE_0 -> DEGREE_90
        DEGREE_90 -> DEGREE_180
        DEGREE_180 -> DEGREE_270
        DEGREE_270 -> DEGREE_0
    }
}