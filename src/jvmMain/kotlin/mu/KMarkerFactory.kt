package mu

import org.slf4j.MarkerFactory

actual object KMarkerFactory {

    actual fun getMarker(name: String): Marker = MarkerFactory.getMarker(name)

}
