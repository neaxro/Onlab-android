package hu.bme.aut.android.securityapp.feature.connecttojob

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConnectToJobScreen(){
    val context = LocalContext.current
    val numberOfDigits = 6
    var digits by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        BasicTextField(
            value = digits,
            onValueChange = {
                if(it.length <= numberOfDigits) {
                    digits = it.trim()
                }
            },
            modifier = Modifier,
            decorationBox = {
                DecoratorBox(
                    numberOfDigits = numberOfDigits,
                    digits = digits,
                )
            },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Characters, autoCorrect = false, KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone = {
                if(digits.length == numberOfDigits){
                    // TODO: connection
                    Log.d("CONNECTJOB_KEYBOARDACTION", "Digits: $digits")
                }
                Log.d("CONNECTJOB_KEYBOARDACTION", "Digits: $digits")
            })
        )
    }
}

@Composable
fun DecoratorBox(
    numberOfDigits: Int,
    digits: String,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(211, 243, 107))
            .border(4.dp, Color.Black, RoundedCornerShape(10.dp))
            .padding(30.dp),
    ) {

        Text(
            text = "Job Pin",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(numberOfDigits){
                val digitValue: String = if(it >= digits.length){
                    ""
                }
                else{
                    digits[it].uppercaseChar().toString()
                }

                Text(
                    text = digitValue,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.White)
                        .border(
                            2.dp,
                            if (digitValue.isEmpty()) Color.Gray else Color.Black,
                            RoundedCornerShape(5.dp)
                        )
                        .padding(10.dp)
                        .width(20.dp)
                )

                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConnectToJobScreenPrev(){
    ConnectToJobScreen()
}