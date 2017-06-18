package mu.internal

internal fun (() -> Any?).toStringSafe(): String {
    try{
        return toString()
    }catch (ex: Exception){
        return "Execution of $this failed: $ex"
    }
}