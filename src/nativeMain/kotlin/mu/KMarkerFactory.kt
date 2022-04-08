package mu

import mu.internal.MarkerNative

public actual object KMarkerFactory {

    public actual fun getMarker(name: String): Marker = MarkerNative(name)
}
