package bo.com.bmsc.core.exception

import kotlinx.serialization.Serializable

@Serializable
enum class ExceptionType{
    AppMaintenance,
    AppSession,
    AppVersionDeprecated,
    AppVersionNotAllowed,
    Connection,
    OldApp,
    ServerError,
    ServerUnderMaintenance,
    Unknown
}