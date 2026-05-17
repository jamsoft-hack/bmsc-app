package bo.com.bmsc.core.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import bmsc.shared.generated.resources.Res
import bmsc.shared.generated.resources.msc_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun BrandLogo(
    modifier: Modifier = Modifier,
    size: Dp = 120.dp,
) {
    Image(
        painter = painterResource(Res.drawable.msc_logo),
        contentDescription = "Mercantil Santa Cruz logo",
        modifier = modifier.size(size),
    )
}
