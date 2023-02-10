package io.github.oshai

/** A platform independent marker to enrich log statements. */
public interface Marker {

  public fun getName(): String
}

internal data class SimpleMarker(private val name: String) : io.github.oshai.Marker {
  override fun getName(): String = this.name
}
