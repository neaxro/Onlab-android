package hu.bme.aut.android.securityapp.ui.feature.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.securityapp.data.model.people.PersonDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonEditor(
    person: PersonDetail = PersonDetail(),
    onPersonChange: (PersonDetail) -> Unit = {},
    readOnly: Boolean = true,
    newPassword: Boolean = false,
    modifier: Modifier = Modifier,
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = person.fullName,
            onValueChange = {
                onPersonChange(person.copy(fullName = it))
            },
            label = {
                Text(text = "Fullname")
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = !readOnly,
        )

        OutlinedTextField(
            value = person.username,
            onValueChange = {
                onPersonChange(person.copy(username = it))
            },
            label = {
                Text(text = "Username")
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = !readOnly,
        )

        OutlinedTextField(
            value = person.nickname,
            onValueChange = {
                onPersonChange(person.copy(nickname = it))
            },
            label = {
                Text(text = "Nickname")
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = !readOnly,
        )

        OutlinedTextField(
            value = person.email,
            onValueChange = {
                onPersonChange(person.copy(email = it))
            },
            label = {
                Text(text = "Email address")
            },
            modifier = Modifier
                .padding(bottom = 5.dp),
            readOnly = readOnly,
            enabled = !readOnly,
        )

        if(newPassword){
            MyDoublePasswordTextFields(
                password = person.password,
                onPasswordChange = { pass1, pass2 ->
                    if(pass1 == pass2){
                        onPersonChange(person.copy(password = pass1))
                    }
                },
                readOnly = readOnly,
                enabled = !readOnly,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PersonEditorPreview(){
    PersonEditor(
        person = PersonDetail("Nemes Axel Roland", "nemesa", "Axi", "neaxro@gmail.com"),
        readOnly = false,
        newPassword = true,
    )
}