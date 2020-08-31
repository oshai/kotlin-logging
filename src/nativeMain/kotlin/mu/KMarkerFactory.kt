package mu

import mu.internal.MarkerLinux

actual object KMarkerFactory {

    actual fun getMarker(name: String): Marker = MarkerLinux(name)
}
