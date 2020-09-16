package mu

import mu.internal.MarkerJS

public actual object KMarkerFactory {

    public actual fun getMarker(name: String): Marker = MarkerJS(name)
}
