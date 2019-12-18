package mu

/**
 * A platform independent marker to enrich log statements.
 */
actual interface Marker {
    actual fun getName(): String

}
