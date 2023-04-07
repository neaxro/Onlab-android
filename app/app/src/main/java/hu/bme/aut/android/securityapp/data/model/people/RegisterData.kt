package hu.bme.aut.android.securityapp.data.model.people

data class RegisterData(
    val email: String,
    val fullName: String,
    val nickname: String,
    val password: String,
    val username: String
)