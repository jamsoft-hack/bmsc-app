package bo.com.bmsc.core.exception

import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.connection_info_check_connection
import bmsc.shared.generated.resources.page_title_error_connection
import bo.com.bmsc.core.domain.CustomException

class TimeoutException(message: String?) : CustomException(
  message = message,
  title = Res.string.page_title_error_connection,
  description = Res.string.connection_info_check_connection,
)
