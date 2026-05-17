package bo.com.bmsc.core.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun InAppWebView(
  url: String,
  modifier: Modifier,
  postData: String?,
  onLoaded: () -> Unit,
  shouldStopBrowsing: (String?) -> Boolean,
) {
  // TODO: apply shared iOS webview
}