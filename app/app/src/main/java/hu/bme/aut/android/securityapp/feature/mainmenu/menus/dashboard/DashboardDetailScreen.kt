package hu.bme.aut.android.securityapp.feature.mainmenu.menus.dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DashboardDetailScreen(
    messageId: Int,
){
    Text(text = messageId.toString())
}