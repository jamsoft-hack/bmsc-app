package bo.com.bmsc.core.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

@Composable
expect fun keyboardHeightAsState(): State<Int>