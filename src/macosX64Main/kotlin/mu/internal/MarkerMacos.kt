package mu.internal

import mu.Marker

internal class MarkerMacos(private val name: String) : Marker {

    override fun getName(): String = this.name
}
