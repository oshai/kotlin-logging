package mu

/**
 * A platform independent factory to create markers.
 */
public expect object KMarkerFactory {

    public fun getMarker(name: String): Marker
}
