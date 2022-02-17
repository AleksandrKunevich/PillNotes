package com.example.pillnotes.presentation.maps

import com.google.gson.annotations.SerializedName

class DirectionResults {
    @SerializedName("routes")
    val routes: List<Route>? = null
}

class Route {
    @SerializedName("overview_polyline")
    val overviewPolyLine: OverviewPolyLine? = null
    val legs: List<Legs>? = null
}

class Legs {
    val steps: List<Steps>? = null
}

class Steps {
    val start_location: Location? = null
    val end_location: Location? = null
    val polyline: OverviewPolyLine? = null
}

class OverviewPolyLine {
    @SerializedName("points")
    var points: String? = null
}

class Location {
    val lat = 0.0
    val lng = 0.0
}