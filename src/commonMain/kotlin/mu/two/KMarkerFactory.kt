package mu.two

/** A platform independent factory to create markers. */
public object KMarkerFactory {

  public fun getMarker(name: String): mu.two.Marker = mu.two.SimpleMarker(name)
}
