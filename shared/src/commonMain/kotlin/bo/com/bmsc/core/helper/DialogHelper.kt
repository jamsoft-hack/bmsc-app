package bo.com.bmsc.core.helper

import androidx.compose.runtime.Composable
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.action_accept
import bmsc.shared.generated.resources.action_cancel
import bmsc.shared.generated.resources.dialog_confirmation_title_attention
import org.jetbrains.compose.resources.StringResource

typealias Callback = (() -> Unit)?

interface IDialogText

open class DialogText(
  val message: StringResource = Res.string.dialog_confirmation_title_attention,
  val title: StringResource? = null,
  val cancelLabel: StringResource? = null,
  val acceptLabel: StringResource? = Res.string.action_accept,
) : IDialogText

open class DialogCustomizable(
  val message: @Composable () -> Unit,
  val cancelLabel: StringResource? = null,
  val acceptLabel: StringResource,
) : IDialogText

open class DialogRawText(
  val message: String,
  val title: String? = null,
  val cancelLabel: String? = null,
  val acceptLabel: String? = null,
) : IDialogText

data class DialogConfig(
  val text: IDialogText,
  val onDismiss: Callback,
  val onAccept: Callback,
  val onCancel: Callback = null,
  val arguments: List<Any> = emptyList(),
)

object DialogHelper {
  private var listener: (settings: DialogConfig?) -> Unit = {}
  private var states: MutableList<DialogConfig> = mutableListOf()
  private var isShownDialog = false

  private fun show(config: DialogConfig?) {
    val resetting = config == null

    if (!resetting && states.indexOf(config) == -1) {
      states.add(config)
    }

    if (states.isNotEmpty() && !resetting && !isShownDialog) {
      isShownDialog = true
      listener(states.first())
    } else if (resetting && isShownDialog) {
      states.removeAt(index = 0)

      val nextElement = states.firstOrNull()
      isShownDialog = nextElement != null

      listener(nextElement)
    }

    println("DIALOG_INFO: $isShownDialog ${config?.text} ${states.size}")
  }

  fun close() {
    show(config = null)
  }

  fun setListener(next: (config: DialogConfig?) -> Unit) {
    listener = next
  }

  fun show(body: DialogText, onAccept: Callback, onDismiss: Callback = null) {
    show(DialogConfig(body, onDismiss, onAccept))
  }

  fun show(message: StringResource, onAccept: Callback, onDismiss: Callback = null) {
    show(DialogConfig(DialogText(message), onDismiss, onAccept))
  }

  fun showClosable(
    message: StringResource,
    cancelLabel: StringResource = Res.string.action_cancel,
    acceptLabel: StringResource = Res.string.action_accept,
    onAccept: Callback,
    onDismiss: Callback = null,
    onCancel: Callback = null,
    title: StringResource? = null,
  ) {
    show(
      config = DialogConfig(
        text = DialogText(message, title, cancelLabel, acceptLabel),
        onDismiss = onDismiss,
        onAccept = onAccept,
        onCancel = onCancel,
      )
    )
  }

  fun show(
    message: String,
    cancelLabel: String? = null,
    acceptLabel: String? = null,
    onAccept: Callback = {},
    onDismiss: Callback = null,
    onCancel: Callback = null,
    title: String? = null,
  ) {
    show(
      config = DialogConfig(
        text = DialogRawText(
          message,
          title = title,
          cancelLabel,
          acceptLabel,
        ),
        onDismiss = onDismiss,
        onAccept = onAccept,
        onCancel = onCancel,
      ),
    )
  }

  fun show(
    message: StringResource,
    title: StringResource? = null,
    acceptLabel: StringResource = Res.string.action_accept,
    arguments: List<Any> = emptyList(),
    onAccept: Callback = {},
    onDismiss: Callback = null,
  ) {
    show(
      config = DialogConfig(
        text = DialogText(message, title, cancelLabel = null, acceptLabel),
        onDismiss,
        onAccept,
        arguments = arguments,
      )
    )
  }

  fun show(
    message: @Composable () -> Unit,
    cancelLabel: StringResource? = null,
    acceptLabel: StringResource = Res.string.action_accept,
    onAccept: Callback = {},
    onDismiss: Callback = null,
  ) {
    show(
      config = DialogConfig(
        text = DialogCustomizable(
          message,
          cancelLabel,
          acceptLabel,
        ),
        onDismiss = onDismiss,
        onAccept = onAccept,
      )
    )
  }
}
