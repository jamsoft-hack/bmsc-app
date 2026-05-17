package bo.com.bmsc.app.theme

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.jvm.JvmInline

@JvmInline
value class AppDimens(val value: Int) {
    companion object {
        // card
        val CardRadius = 14.dp
        val CardBlur = 20.dp
        // button
        val ButtonRadius = 14.dp
        val ButtonHeight = 48.dp
        val TextButtonHeight = 24.dp
        // content
        val ScreenSpacer = 40.dp
        val ContentPadding = 20.dp
        val MenuItemContentPadding = 16.dp
        val ContentPaddingSmall = 8.dp
        val ContentPaddingMedium = 12.dp
        // Max widths
        val MaxContentWidth = 600.dp
        val BodyLineHeight = 24.sp
    }
}