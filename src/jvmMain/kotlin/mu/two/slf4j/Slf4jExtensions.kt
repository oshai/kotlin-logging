package mu.two.slf4j

import mu.two.Marker
import org.slf4j.MarkerFactory

public fun mu.two.Marker.toSlf4j(): org.slf4j.Marker = MarkerFactory.getMarker(this.getName())
