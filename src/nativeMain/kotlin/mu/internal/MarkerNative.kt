package mu.internal

import mu.Marker

internal class MarkerNative(private val name: String) : Marker {

    override fun getName(): String = this.name
}
