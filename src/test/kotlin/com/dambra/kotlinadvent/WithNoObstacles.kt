package com.dambra.kotlinadvent

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class WithNoObstacles {
    @Test
    fun `can route from start to finish`() {
        val route = """...........
        .......S...
        ...........
        ...........
        ...........
        ...........
        ..X........""".findRoute()

        assertThat(route).isEqualTo("""...........
.......*...
......*....
.....*.....
....*......
...*.......
..*........""")
    }
}

private fun String.findRoute(): String {
    return this
}
