package hu.bme.aut.android.securityapp.ui.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class MoreMenu(
    val title: String = "",
    val icon: ImageVector? = null,
    val color: Color = Color.Black,
    val backgroundColor: Color = Color.LightGray,
    val url: String = ""
)
