val hostOs = System.getProperty("os.name")
val isMacos   by extra(hostOs == "Mac OS X")
val isLinux   by extra(hostOs == "Linux")
val isWindows by extra(hostOs.startsWith("Windows"))

extra["hostPresetName"] = when {
    isMacos -> "macosX64"
    isLinux -> "linuxX64"
    isWindows -> "mingwX64"
    else -> error("Unsupported host platform")
}
