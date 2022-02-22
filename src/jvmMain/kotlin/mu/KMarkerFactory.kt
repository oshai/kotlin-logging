package mu

import org.slf4j.MarkerFactory

public actual object KMarkerFactory {

    /**
    * Retrieves a [Marker] with the given name. Shorthand for
    * [MarkerFactory.getMarker].
    * */
    public actual fun getMarker(name: String): Marker = MarkerFactory.getMarker(name)

}

/**
 * Shorthand for [KMarkerFactory].
 * */
val markerFactory = KMarkerFactory

/**
 * Retrieves a [Marker] with the given name. Shorthand for
 * [KMarkerFactory.getMarker].
 * */
operator fun KMarkerFactory.get(string: String) = getMarker(string)

/**
 * Returns a detached [Marker] with the given names. Shorthand for
 * [MarkerFactory.getDetachedMarker]
 * */
fun detachedMarker(string: String) = MarkerFactory.getDetachedMarker(string)!!

/**
 * Returns a detached [Marker] with the given names. Shorthand for
 * [MarkerFactory.getDetachedMarker].
 *
 * It's recommended to use [detachedMarker] instead, but there's really no difference.
 * */
fun KMarkerFactory.detachedMarker(string: String) = MarkerFactory.getDetachedMarker(string)!!

/**
 * Adds a reference to [marker]. Shorthand for [Marker.add]
 * */
operator fun Marker.plusAssign(marker: Marker) = add(marker)

/**
 * Removes a reference to [marker]. Shorthand for [Marker.remove].
 * */
operator fun Marker.minusAssign(marker: Marker) {
    remove(marker)
}

