package bo.com.bmsc.core.composable.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun SnackbarConIcono(mensaje: String, onIconClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(Color.White, shape = RoundedCornerShape(0.dp))
            .drawBehind {
                val strokeWidth = 4.dp.toPx()
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = strokeWidth
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = mensaje,
            color = Color.Black,
            modifier = Modifier
                .weight(0.8f)
                .padding(15.dp),
            overflow = TextOverflow.Ellipsis
        )
        Box(
            modifier = Modifier
                .weight(0.2f),
            contentAlignment = Alignment.Center
        ) {
            IconButton(onClick = {
                onIconClick()
            } ) {
//                Icon(
//                    modifier = Modifier.size(60.dp),
//                    imageVector = BarikIcons.Close,
//                    contentDescription = "Cerrar",
//                    tint = Color.Black
//                )
            }
        }
    }
}