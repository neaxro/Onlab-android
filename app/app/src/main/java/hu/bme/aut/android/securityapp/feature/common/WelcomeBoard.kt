package hu.bme.aut.android.securityapp.feature.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.people.getProfileBitmap

@Composable
fun WelcomeBoard(
    person: Person,
    modifier: Modifier = Modifier
){
    Surface(
        modifier = modifier.clip(RoundedCornerShape(15.dp)),
        //shadowElevation = 5.dp,
        //tonalElevation = 5.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(211, 243, 107), RoundedCornerShape(15.dp))

        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = "Good morning!",
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = person.fullName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            if (person.profilePicture != null) {
                Image(
                    bitmap = person.getProfileBitmap(),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .padding(end = 10.dp)
                )
            }
            else{
                Image(
                    imageVector = Icons.Rounded.Person,
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .padding(end = 10.dp)
                )
            }


        }
    }
}