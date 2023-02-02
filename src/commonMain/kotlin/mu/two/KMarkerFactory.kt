package io.github.oshai

/** A platform independent factory to create markers. */
public object KMarkerFactory {

  public fun getMarker(name: String): io.github.oshai.Marker = io.github.oshai.SimpleMarker(name)
}
