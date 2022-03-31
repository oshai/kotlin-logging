package mu
public interface LoggingCallback {
    public operator fun invoke (message: String)

    public operator fun invoke (marker: Marker?, t: Throwable?, message: String)

    public operator fun invoke (marker: Marker?, message: String)

    public operator fun invoke (t: Throwable?, message: String)
}
