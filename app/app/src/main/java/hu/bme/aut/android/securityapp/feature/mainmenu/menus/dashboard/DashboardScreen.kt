package hu.bme.aut.android.securityapp.feature.mainmenu.menus

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.data.model.dashboard.Dashboard
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.feature.common.DashboardCard
import hu.bme.aut.android.securityapp.feature.common.WelcomeBoard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(){

    val das = listOf(
        Dashboard(1, "Üdvözlő üzenet", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus at massa vel neque porta finibus eget ac mi. Mauris eleifend ipsum id ultrices feugiat. Aenean tempus nec eros at tempus. Morbi feugiat vehicula orci quis iaculis. Vestibulum purus nulla, fringilla porta mauris a, semper blandit dolor. Etiam varius porta sem, vel scelerisque nisi sodales quis. Quisque laoreet diam eget tortor malesuada tempus. Nullam ut urna eleifend, egestas ligula sit amet, mollis arcu. Cras sodales eros a justo sollicitudin auctor. In lectus felis, dapibus sit amet cursus et, pulvinar maximus nunc.", "2023-03-27T22:13:17.1233333", "Nemes Axel Roland", null, 1, "Sima"),
        Dashboard(1, "Üdvözlő üzenet", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus at massa vel neque porta finibus eget ac mi. Mauris eleifend ipsum id ultrices feugiat. Aenean tempus nec eros at tempus. Morbi feugiat vehicula orci quis iaculis. Vestibulum purus nulla, fringilla porta mauris a, semper blandit dolor. Etiam varius porta sem, vel scelerisque nisi sodales quis. Quisque laoreet diam eget tortor malesuada tempus. Nullam ut urna eleifend, egestas ligula sit amet, mollis arcu. Cras sodales eros a justo sollicitudin auctor. In lectus felis, dapibus sit amet cursus et, pulvinar maximus nunc.", "2023-03-27T22:13:17.1233333", "Nemes Axel Roland", null, 1, "Sima"),
        Dashboard(1, "Üdvözlő üzenet", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus at massa vel neque porta finibus eget ac mi. Mauris eleifend ipsum id ultrices feugiat. Aenean tempus nec eros at tempus. Morbi feugiat vehicula orci quis iaculis. Vestibulum purus nulla, fringilla porta mauris a, semper blandit dolor. Etiam varius porta sem, vel scelerisque nisi sodales quis. Quisque laoreet diam eget tortor malesuada tempus. Nullam ut urna eleifend, egestas ligula sit amet, mollis arcu. Cras sodales eros a justo sollicitudin auctor. In lectus felis, dapibus sit amet cursus et, pulvinar maximus nunc.", "2023-03-27T22:13:17.1233333", "Nemes Axel Roland", null, 1, "Sima"),
        Dashboard(1, "Üdvözlő üzenet", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus at massa vel neque porta finibus eget ac mi. Mauris eleifend ipsum id ultrices feugiat. Aenean tempus nec eros at tempus. Morbi feugiat vehicula orci quis iaculis. Vestibulum purus nulla, fringilla porta mauris a, semper blandit dolor. Etiam varius porta sem, vel scelerisque nisi sodales quis. Quisque laoreet diam eget tortor malesuada tempus. Nullam ut urna eleifend, egestas ligula sit amet, mollis arcu. Cras sodales eros a justo sollicitudin auctor. In lectus felis, dapibus sit amet cursus et, pulvinar maximus nunc.", "2023-03-27T22:13:17.1233333", "Nemes Axel Roland", null, 1, "Sima")
    )

    val lazyListState = rememberLazyListState()

    val person by remember {
        mutableStateOf(Person(0, "Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com", null))
    }

    val messages = remember {
        mutableStateOf<List<Dashboard>>(das)
    }

    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 10.dp,
                tonalElevation = 5.dp
            ){
                TopAppBar(
                    title = {
                        Text(
                            text = "Dashboard",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    ),
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
        ) {
            Column() {
                WelcomeBoard(
                    person,
                    modifier = Modifier.padding(10.dp)
                )

                LazyColumn(
                    state = lazyListState,
                    contentPadding = PaddingValues(10.dp)
                ) {
                    items(das){message ->
                        DashboardCard(
                            message,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenDasboard(){
    DashboardScreen()
    //WelcomeBoard(Person(0, "Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com", null))
}