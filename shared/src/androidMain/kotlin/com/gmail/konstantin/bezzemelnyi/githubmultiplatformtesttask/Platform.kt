package com.gmail.konstantin.bezzemelnyi.githubmultiplatformtesttask

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}