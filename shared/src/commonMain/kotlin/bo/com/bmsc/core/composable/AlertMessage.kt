package bo.com.bmsc.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppColors
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.core.helper.parseMarkdownToAnnotatedString
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

enum class AlertMessageVariant(
    var color: Color = Color.Unspecified,
    var contrast: Color = Color.Unspecified,
    var text: Color = Color.Unspecified,
) {
    Success,
    Disabled,
    Warning,
    Danger,
}

@Composable
private fun pickVariantColor(variant: AlertMessageVariant): AlertMessageVariant {
    when (variant) {
        AlertMessageVariant.Success -> {
            variant.color = AppColors.reactive.success
            variant.contrast = AppColors.reactive.successContrast
            variant.text = AppColors.reactive.textSuccessContrast
        }

        AlertMessageVariant.Danger -> {
            variant.color = AppColors.reactive.danger
            variant.contrast = AppColors.reactive.dangerContrast
            variant.text = AppColors.reactive.textDangerContrast
        }

        AlertMessageVariant.Disabled -> {
            variant.color = AppColors.Neutral
            variant.contrast = AppColors.reactive.disabledContrast
            variant.text = AppColors.reactive.textDefault
        }

        AlertMessageVariant.Warning -> {
            variant.color = AppColors.reactive.warning
            variant.contrast = AppColors.reactive.warningContrast
            variant.text = AppColors.reactive.textWarningContrast
        }
    }

    return variant
}

@Composable
private fun AnnotatedAlertMessage(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    variant: AlertMessageVariant = AlertMessageVariant.Disabled,
) {
    val freshColor = pickVariantColor(variant)

    Box(
        modifier = modifier
        .background(freshColor.color.copy(alpha = .1f), MaterialTheme.shapes.medium)
        .border(1.dp, freshColor.contrast, MaterialTheme.shapes.medium)
        .padding(vertical = 12.dp, horizontal = 16.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = freshColor.text,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Composable
fun AlertMessage(
    text: String,
    modifier: Modifier = Modifier,
    variant: AlertMessageVariant = AlertMessageVariant.Disabled,
    onClick: (() -> Unit)? = null,
) {
    val freshColor = pickVariantColor(variant)

    val content: @Composable ColumnScope.() -> Unit = {
        Box(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp).composed { modifier },
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = freshColor.text,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
    val colors = CardDefaults.cardColors(
        containerColor = freshColor.color.copy(alpha = .1f), contentColor = freshColor.text
    )
    val cardModifier = Modifier
            .widthIn(max = AppDimens.MaxContentWidth)
            .border(1.dp, freshColor.contrast, MaterialTheme.shapes.medium)

    if(onClick != null) {
        Card(
            modifier = cardModifier,
            onClick = onClick,
            shape = MaterialTheme.shapes.medium,
            colors = colors,
            content = content
        )
    } else {
        Card(
            modifier = cardModifier,
            shape = MaterialTheme.shapes.medium,
            colors = colors,
            content = content
        )
    }
}

@Composable
fun ChipDuoTone(
    text: String,
    modifier: Modifier = Modifier,
    variant: AlertMessageVariant = AlertMessageVariant.Disabled,
) {
    val color = when (variant) {
        AlertMessageVariant.Danger -> AppColors.reactive.textDanger
        AlertMessageVariant.Warning -> AppColors.reactive.textLucked
        AlertMessageVariant.Success -> AppColors.reactive.textSuccess
        else -> AppColors.reactive.textInfo
    }

    Box(
        modifier = modifier
            .background(color.copy(alpha = .1f), MaterialTheme.shapes.small)
            .border(1.dp, color, MaterialTheme.shapes.small)
            .padding(8.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            softWrap = false,
            color = color,
        )
    }
}

@Composable
fun AlertMessageError(
    text: String,
    modifier: Modifier = Modifier
) {
    AlertMessage(text, modifier, AlertMessageVariant.Danger)
}

@Composable
fun AlertMessageError(
    text: StringResource,
    modifier: Modifier = Modifier
) {
    AlertMessage(stringResource(text), modifier, AlertMessageVariant.Danger)
}

@Composable
fun AlertMessageSuccess(
    text: StringResource,
    modifier: Modifier = Modifier
) {
    AlertMessage(stringResource(text), modifier, AlertMessageVariant.Success)
}

@Composable
fun AlertMessageWarning(
    text: StringResource,
    modifier: Modifier = Modifier
) {
    AlertMessage(stringResource(text), modifier, AlertMessageVariant.Warning)
}

@Composable
fun ChipDuoToneWarning(
    text: StringResource,
    modifier: Modifier = Modifier
) {
    ChipDuoTone(stringResource(text), modifier, AlertMessageVariant.Warning)
}

@Composable
fun ChipDuoToneError(
    text: StringResource,
    modifier: Modifier = Modifier
) {
    ChipDuoTone(stringResource(text), modifier, AlertMessageVariant.Danger)
}

@Composable
fun ChipDuoToneActive(
    text: StringResource,
    modifier: Modifier = Modifier,
) {
    ChipDuoTone(stringResource(text), modifier, AlertMessageVariant.Success)
}

@Composable
fun AlertMessageDisabled(
    text: String,
    modifier: Modifier = Modifier,
) {
    AlertMessage(text, modifier, AlertMessageVariant.Disabled)
}

@Composable
fun AlertMessageDisabled(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    AnnotatedAlertMessage(text, modifier, AlertMessageVariant.Disabled)
}

@Composable
fun AlertMessageWarning(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    AnnotatedAlertMessage(text, modifier, AlertMessageVariant.Warning)
}

@Composable
fun ChipDuoToneDisabled(
    text: StringResource,
    modifier: Modifier = Modifier,
) {
    ChipDuoTone(stringResource(text), modifier, AlertMessageVariant.Disabled)
}
