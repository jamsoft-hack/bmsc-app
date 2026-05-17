package bo.com.bmsc.core.helper

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import bmsc.shared.generated.resources.Res
import bo.com.bmsc.core.composable.ToastVariant
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

private val styleMap: MutableMap<String, SpanStyle> = mutableMapOf(
  "**" to SpanStyle(fontWeight = FontWeight.Bold),
  "*" to SpanStyle(fontStyle = FontStyle.Italic),
  "==" to SpanStyle()
)

val linkQueue: MutableList<String> = mutableListOf()

@Composable
fun parseMarkdownToAnnotatedString(
  textRes: StringResource,
  vararg formatArgs: Any
): AnnotatedString {
  val text = stringResource(textRes, *formatArgs)

  styleMap["=="] = SpanStyle(color = MaterialTheme.colorScheme.primary)

  return buildAnnotatedString {
    parseText(text, builder = this)
  }
}

@Composable
fun parseMarkdownToAnnotatedString(text: String): AnnotatedString {
  styleMap["=="] = SpanStyle(color = MaterialTheme.colorScheme.primary)

  return buildAnnotatedString {
    parseText(text, builder = this)
  }
}

@Composable
fun parseClickableAnnotatedString(
  textRes: StringResource,
  vararg formatArgs: String
): AnnotatedString {
  linkQueue.addAll(formatArgs)

  val annotated = parseMarkdownToAnnotatedString(textRes)

  linkQueue.clear()

  return annotated
}

private fun parseText(text: String, builder: AnnotatedString.Builder) {
  var remainingText = text

  while (remainingText.isNotEmpty()) {
    var foundMatch = false
    var earliestIndex = Int.MAX_VALUE
    var matchedToken = ""
    var matchedStyle: SpanStyle? = null

    for ((token, style) in styleMap) {
      val startIndex = remainingText.indexOf(token)
      if (startIndex != -1 && startIndex < earliestIndex) {
        val endTokenIndex = remainingText.indexOf(token, startIndex + token.length)
        if (endTokenIndex != -1) {
          earliestIndex = startIndex
          matchedToken = token
          matchedStyle = style
          foundMatch = true
        }
      }
    }

    if (foundMatch && matchedStyle != null) {
      builder.append(remainingText.substring(0, earliestIndex))

      val endTokenIndex = remainingText.indexOf(matchedToken, earliestIndex + matchedToken.length)
      val content = remainingText.substring(earliestIndex + matchedToken.length, endTokenIndex)

      if (matchedToken == "==" && linkQueue.isNotEmpty()) {
        val currentLink = linkQueue.first()

        builder.pushLink(
          LinkAnnotation.Clickable(
            tag = content,
            styles = TextLinkStyles(matchedStyle.copy(textDecoration = TextDecoration.Underline)),
            linkInteractionListener = {
//              if (!openWebBrowser(url = currentLink)) {
//                ToastHelper.show(
//                  Res.string.unavailable_external_browser,
//                  ToastVariant.Disabled,
//                )
//              }
            },
          ),
        )

        linkQueue.removeAt(index = 0)
      } else {
        builder.pushStyle(matchedStyle)
      }
      parseText(content, builder)
      builder.pop()

      remainingText = remainingText.substring(endTokenIndex + matchedToken.length)
    } else {
      builder.append(remainingText)
      break
    }
  }
}
