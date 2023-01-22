package mu

public actual object KMarkerFactory {

  public actual fun getMarker(name: String): Marker = SimpleMarker(name)
}
