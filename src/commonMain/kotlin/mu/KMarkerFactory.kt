package mu

/** A platform independent factory to create markers. */
public object KMarkerFactory {

  public fun getMarker(name: String): Marker = SimpleMarker(name)
}
