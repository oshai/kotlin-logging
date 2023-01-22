package mu

/** A platform independent marker to enrich log statements. */
public interface Marker {

  public fun getName(): String
}

internal data class SimpleMarker(private val name: String) : Marker {
    override fun getName(): String = this.name
}
