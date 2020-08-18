package mu

import mu.internal.MarkerMacos

actual object KMarkerFactory {

    actual fun getMarker(name: String): Marker = MarkerMacos(name)
}
