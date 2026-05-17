package bo.com.bmsc.core.exception

import kotlinx.serialization.Serializable

@Serializable
data class MainException(
    val type: ExceptionType = ExceptionType.Unknown,
    val exceptionMessage: String? = null
) : Throwable() {
    val anyOldVersion: Boolean = type in listOf(
        ExceptionType.OldApp,
        ExceptionType.AppVersionDeprecated,
        ExceptionType.AppVersionNotAllowed
    )
}
