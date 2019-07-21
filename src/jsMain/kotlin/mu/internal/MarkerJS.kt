package mu.internal

import mu.Marker

internal class MarkerJS(private val name: String) : Marker {

    override fun getName(): String = this.name
}