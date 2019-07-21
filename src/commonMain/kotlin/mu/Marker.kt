package mu

/**
 * A platform independent marker to enrich log statements.
 */
expect interface Marker {

    fun getName(): String
}
