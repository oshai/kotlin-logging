package mu

import mu.internal.MarkerLinux

public actual object KMarkerFactory {

    public actual fun getMarker(name: String): Marker = MarkerLinux(name)
}
