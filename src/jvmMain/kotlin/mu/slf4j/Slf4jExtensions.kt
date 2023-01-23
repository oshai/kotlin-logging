package mu.slf4j

import mu.Marker
import org.slf4j.MarkerFactory

public fun Marker.toSlf4j(): org.slf4j.Marker = MarkerFactory.getMarker(this.getName())
