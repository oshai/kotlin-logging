package mu

import org.slf4j.MarkerFactory

public actual object KMarkerFactory {

    public actual fun getMarker(name: String): Marker = MarkerFactory.getMarker(name)

}
