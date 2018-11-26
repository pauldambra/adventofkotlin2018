package com.dambra.kotlinadvent.week1

fun distanceBetween(a: Coordinate, b: Coordinate) = Math.sqrt(
        (Math.pow(((a.x - b.x).toDouble()), 2.0))
                +
                (Math.pow(((a.y - b.y).toDouble()), 2.0))
)

data class CoordDistancePair(val distance: Double, val coordinate: Coordinate)
data class Step(val current: Coordinate, val route: List<Coordinate>, val finish: Coordinate)

private fun walkRoute(step: Step): Step {
    val next = step.current.generateSurroundingCoordinates()
            .map {
                CoordDistancePair(distanceBetween(it, step.finish), it)
            }
            .minBy {
                it.distance
            }!!.coordinate

    val nextStep = Step(next, step.route + next, step.finish)

    return if (next == step.finish)
        nextStep
    else
        walkRoute(nextStep)

}

private fun rowGenerator(rowWidth: Int, route: List<Coordinate>) = { y:Int ->
    (0..rowWidth)
            .map { x -> route.firstOrNull { it.x == x && it.y == y } }
            .map { if (it == null) "." else "*" }
            .fold("") { acc, c -> acc + c }
}

private fun templateRouteOntoMap(map: AdventMap, route: List<Coordinate>): String {
    val generateRow = rowGenerator(map.width-1, route)
    return (0..(map.height - 1))
            .map(generateRow)
            .fold("") { acc, s -> acc + "$s\n" }
            .trim()
}

fun String.findRoute(): String {
    val map = AdventMap.parseFrom(this)

    val firstStep = Step(
            map.startCoordinate,
            mutableListOf(map.startCoordinate),
            map.finishCoordinate
    )

    val walkedRoute = walkRoute(firstStep)

    return templateRouteOntoMap(map, walkedRoute.route)
}