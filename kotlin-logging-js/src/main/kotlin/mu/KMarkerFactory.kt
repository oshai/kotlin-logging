package mu

import mu.internal.MarkerJS

actual object KMarkerFactory {

    actual fun getMarker(name: String): Marker = MarkerJS(name)
}