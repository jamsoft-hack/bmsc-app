package bo.com.bmsc.core.domain

import androidx.compose.ui.graphics.vector.ImageVector
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.connection_info_check_connection
import bmsc.shared.generated.resources.connection_info_old_app_version
import bmsc.shared.generated.resources.connection_info_server_error
import bmsc.shared.generated.resources.connection_info_server_maintenance
import bmsc.shared.generated.resources.connection_info_unknown_server_error
import bmsc.shared.generated.resources.page_title_error_app_maintenance
import bmsc.shared.generated.resources.page_title_error_connection
import bmsc.shared.generated.resources.page_title_error_old_app
import bmsc.shared.generated.resources.page_title_error_server
import bmsc.shared.generated.resources.page_title_error_unknown
//import bo.com.bmsc.assets.BarikIcons
//import bo.com.bmsc.assets.barikicons.DoesNotRefresh
import org.jetbrains.compose.resources.StringResource

open class CustomException(
  override val message: String?,
  val title: StringResource? = null,
  val description: StringResource? = null,
  val icon: ImageVector? = null,
) : Exception(message) {
  constructor(message: String) : this(message = message, title = null, description = null)
}

open class NoInternetException(
  message: String?,
) : CustomException(
  message = message,
  title = Res.string.page_title_error_connection,
  description = Res.string.connection_info_check_connection,
)

class UnavailableException(
  message: String? = null,
) : CustomException(
  message = message,
  title = Res.string.page_title_error_server,
  description = Res.string.connection_info_server_error,
)

class RequestCancelledException(
  message: String? = null,
) : CustomException(
  message = message,
  title = Res.string.page_title_error_server,
  description = Res.string.connection_info_server_error,
)

class AppVersionNotAllowed(
  message: String? = null,
  title: StringResource = Res.string.page_title_error_old_app,
  description: StringResource = Res.string.connection_info_old_app_version,
) : CustomException(
  message = message,
  description = description,
  title = title,
//  icon = BarikIcons.DoesNotRefresh,
)

class AppMaintenance(
  message: String? = null,
  title: StringResource = Res.string.page_title_error_app_maintenance,
  description: StringResource = Res.string.connection_info_server_maintenance,
) : CustomException(
  message = message,
  description = description,
  title = title,
)

class InvalidArgument(
  message: String?,
) : CustomException(message = message)

class UnknownError(
  message: String?,
  title: StringResource = Res.string.page_title_error_unknown,
  description: StringResource = Res.string.connection_info_unknown_server_error,
) : CustomException(
  message = message,
  description = description,
  title = title,
) {
  constructor(message: String?) : this(
    message,
    title = Res.string.page_title_error_unknown,
    description = Res.string.connection_info_unknown_server_error,
  )
}

class PermissionDenied(message: String?) : CustomException(message = message)

class Unauthenticated(message: String?) : CustomException(message = message)
