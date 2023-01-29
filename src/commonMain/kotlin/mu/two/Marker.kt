package mu.two

/** A platform independent marker to enrich log statements. */
public interface Marker {

  public fun getName(): String
}

internal data class SimpleMarker(private val name: String) : mu.two.Marker {
  override fun getName(): String = this.name
}
