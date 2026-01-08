module io.github.oshai.kotlinlogging {
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires org.slf4j;
    requires java.logging;

    exports io.github.oshai.kotlinlogging;
    exports io.github.oshai.kotlinlogging.coroutines;
    exports io.github.oshai.kotlinlogging.logback;
    exports io.github.oshai.kotlinlogging.slf4j;
}
