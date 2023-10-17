package hu.bme.aut.android.securityapp.constants

fun validateUserFullName(fullname: String): DataFieldErrors {
    if(fullname.isEmpty()) return DataFieldErrors.FullNameError("Fullname is empty!")
    if(fullname.length > Constants.MAX_USER_FULLNAME_LENGTH) return DataFieldErrors.FullNameError("Fullname is too long!")

    return DataFieldErrors.NoError
}

fun validateUserUsername(username: String): DataFieldErrors {
    if(!username.matches(Regex("\\S+"))) return DataFieldErrors.UserNameError("Username must not contain whitespaces!")

    return DataFieldErrors.NoError
}

fun validateUserNickname(nickname: String): DataFieldErrors {
    if(!nickname.matches(Regex("\\S+"))) return DataFieldErrors.NickNameError("Nickname must not contain whitespaces!")
    if(!nickname.matches(Regex("^[a-zA-Z]+\$"))) return DataFieldErrors.NickNameError("Nickname must contains only letters!")

    return DataFieldErrors.NoError
}

fun validateUserEmail(email: String): DataFieldErrors {
    if(!email.matches(Regex("^[\\w-]+@([\\w-]+\\.)+[\\w-]{2,4}\$"))) return DataFieldErrors.EmailAddressError("Invalid email address format!")

    return DataFieldErrors.NoError
}

fun validateUserPassword(password: String): DataFieldErrors {
    if(password.length < Constants.MIN_USER_PASSWORD_LENGTH) return DataFieldErrors.PasswordError("Password is too short!")
    if(password.length > Constants.MAX_USER_PASSWORD_LENGTH) return DataFieldErrors.PasswordError("Password is too long!")

    return DataFieldErrors.NoError
}

fun validateConnectionPin(pin: String, numberOfDigits: Int): DataFieldErrors {
    if(pin.length != numberOfDigits) return DataFieldErrors.ConnectionPinError("Pin must be $numberOfDigits long!")
    if(!pin.matches(Regex("^[A-Z0-9]+\$"))) return DataFieldErrors.ConnectionPinError("Invalid format!")

    return DataFieldErrors.NoError
}

fun validateJobTitle(title: String): DataFieldErrors{
    if(title.isEmpty()) return DataFieldErrors.JobTitleError("Title is empty!")
    if(title.length > Constants.MAX_JOB_TITLE_LENGTH) return DataFieldErrors.JobTitleError("Title is too long!")
    if(!title.matches(Regex("^[a-z0-9A-Z\\s]+\$"))) return DataFieldErrors.ConnectionPinError("Invalid format!")

    return DataFieldErrors.NoError
}

fun validateJobDescription(description: String): DataFieldErrors{
    if(description.length > Constants.MAX_JOB_DESCRIPTION_LENGTH) return DataFieldErrors.JobTitleError("Title is too long!")

    return DataFieldErrors.NoError
}

sealed class DataFieldErrors(val message: String){
    object NoError: DataFieldErrors("")
    class FullNameError(val msg: String): DataFieldErrors(message = msg)
    class UserNameError(val msg: String): DataFieldErrors(message = msg)
    class NickNameError(val msg: String): DataFieldErrors(message = msg)
    class EmailAddressError(val msg: String): DataFieldErrors(message = msg)
    class PasswordError(val msg: String): DataFieldErrors(message = msg)
    class PasswordMissmachError(val msg: String): DataFieldErrors(message = msg)
    class ConnectionPinError(val msg: String): DataFieldErrors(message = msg)
    class JobTitleError(val msg: String): DataFieldErrors(message = msg)
}