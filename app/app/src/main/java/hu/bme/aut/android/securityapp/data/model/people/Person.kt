package hu.bme.aut.android.securityapp.data.model.people

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.util.Base64

data class Person(
    val id: Int = 0,
    val fullName: String = "",
    val username: String = "",
    val nickname: String = "",
    val email: String = "",
    val profilePicture: String? = null
    )

fun Person.getProfileBitmap(): ImageBitmap{
    val imageBytes = Base64.getDecoder().decode(this.profilePicture)
    val image: Bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    return image.asImageBitmap()
}