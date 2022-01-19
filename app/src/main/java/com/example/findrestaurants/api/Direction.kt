package com.example.findrestaurants.api

data class Direction (
    val routes: List<Route>
)

data class Route (
    val overview_polyline: Line
)

data class Line (
    val points: String
)