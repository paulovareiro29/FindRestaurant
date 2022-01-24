package com.example.findrestaurants.api

data class Direction (
    val routes: List<Route>
)

data class Route (
    val overview_polyline: Line,
    val legs: Array<Leg>
)

data class Leg (
    val duration: LegDuration
)

data class LegDuration (
    val text: String
)

data class Line (
    val points: String
)