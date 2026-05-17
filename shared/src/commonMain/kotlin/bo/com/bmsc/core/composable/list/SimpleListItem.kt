package bo.com.bmsc.core.composable.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import bo.com.bmsc.app.theme.AppDimens
import bo.com.bmsc.core.helper.parseMarkdownToAnnotatedString

@Composable
fun SimpleListItem(
    modifier: Modifier = Modifier,
    iconLeft: ImageVector? = null,
    iconRight: ImageVector? = null,
    title: String,
    subtitle: String? = null,
    titleStyle: TextStyle? = null,
    subtitleStyle: TextStyle? = null,
    onPressed: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(AppDimens.ContentPaddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(
                enabled = onPressed != null,
                role = Role.Button,
                onClick = { onPressed?.invoke() }
            )
            .padding(AppDimens.MenuItemContentPadding)
            .semantics(mergeDescendants = true) {
                contentDescription = title
            }
    ) {
        iconLeft?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = parseMarkdownToAnnotatedString(title),
                style = titleStyle ?: MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            subtitle?.let {
                Text(
                    text = parseMarkdownToAnnotatedString(it),
                    style = subtitleStyle ?: MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        iconRight?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
