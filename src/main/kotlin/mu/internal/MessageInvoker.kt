package mu.internal

internal fun (() -> Any?).toStringSafe(): String {
    try{
        return invoke().toString()
    }catch (ex: Exception){
        return "Execution of $this failed: $ex"
    }
}