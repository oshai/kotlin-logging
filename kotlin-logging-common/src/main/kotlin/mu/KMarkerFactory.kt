package mu

/**
 * A platform independent factory to create markers.
 */
expect object KMarkerFactory {

    fun getMarker(name: String): Marker
}
