package io.github.oshai.kotlinlogging.slf4j.internal

import io.github.oshai.kotlinlogging.Marker

internal class Slf4jMarker(val marker: org.slf4j.Marker) : Marker, org.slf4j.Marker by marker
