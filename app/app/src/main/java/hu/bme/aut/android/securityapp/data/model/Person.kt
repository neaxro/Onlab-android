package hu.bme.aut.android.securityapp.data.model

data class Person(
    val id: Int,
    val fullName: String,
    val username: String,
    val nickname: String,
    val email: String,
    val profilePicture: String?
    )