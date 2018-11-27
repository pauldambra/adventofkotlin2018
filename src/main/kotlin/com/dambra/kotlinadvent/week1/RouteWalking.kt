package com.dambra.kotlinadvent.week1

fun distanceBetween(a: Coordinate, b: Coordinate) = Math.sqrt(
        (Math.pow(((a.x - b.x).toDouble()), 2.0))
                +
                (Math.pow(((a.y - b.y).toDouble()), 2.0))
)

data class CoordDistancePair(val distance: Double, val coordinate: Coordinate)
data class Step(val map: AdventMap, val current: Coordinate, val route: List<Coordinate>, val finish: Coordinate)

private fun walkRoute(steps: List<Step>): Step {
    val nextSteps = steps.flatMap { step ->
        val currentDistance = distanceBetween(step.current, step.finish)
        step.map.generateSurroundingCoordinates(step.current)
                .map {
                    CoordDistancePair(distanceBetween(it, step.finish), it)
                }
                .filter { it.distance <= currentDistance }
                .sortedBy { it.distance }
                .map { it.coordinate }
                .map { Step(step.map, it, step.route + it, step.finish) }
    }

    return if (nextSteps.any { it.current == it.finish })
        nextSteps.minBy { it.route.size }!!
            else
        walkRoute(nextSteps)

}

data class RoutePrintingPair(val x: Int, val route: Coordinate?)

private fun rowGenerator(map: AdventMap, route: List<Coordinate>) = { y: Int ->
    (0..(map.width - 1))
            .map { x -> RoutePrintingPair(x, route.firstOrNull { it.x == x && it.y == y }) }
            .map { if (it.route == null) map.printAt(it.x, y) else "*" }
            .fold("") { acc, c -> acc + c }
}

private fun templateRouteOntoMap(map: AdventMap, route: List<Coordinate>): String {
    val generateRow = rowGenerator(map, route)
    return (0..(map.height - 1))
            .map(generateRow)
            .fold("") { acc, s -> acc + "$s\n" }
            .trim()
}

fun String.findRoute(): String {
    val map = AdventMap.parseFrom(this)

    val firstStep = Step(
            map,
            map.startCoordinate,
            mutableListOf(map.startCoordinate),
            map.finishCoordinate
    )

    val walkedRoute = walkRoute(listOf(firstStep))

    return templateRouteOntoMap(map, walkedRoute.route)
}