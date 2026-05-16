package bo.com.bmsc

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform