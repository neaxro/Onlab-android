package hu.bme.aut.android.securityapp.constants

fun validateUserFullName(fullname: String): DataFieldErrors {
    if(fullname.isEmpty()) return DataFieldErrors.FullNameError("Fullname is empty!")
    if(fullname.length > Constants.MAX_USER_FULLNAME_LENGTH) return DataFieldErrors.FullNameError("Fullname is too long!")
    if(containsNumbers(fullname)) return DataFieldErrors.FullNameError("Contains numbers!")
    if(containsSpecialCharacters(fullname)) return DataFieldErrors.FullNameError("Contains special characters!")
    if(startsOrEnsWithWhitespace(fullname)) return DataFieldErrors.FullNameError("Starts or ends witch whitespace!")
    if(containsMoreThan3Words(fullname)) return DataFieldErrors.FullNameError("Contains more than 3 words!")
    if(!fullname.matches(Regex("^[A-Za-z]+(?: [A-Za-z]+){0,2}\$"))) return DataFieldErrors.FullNameError("Invalid character(s)!")

    return DataFieldErrors.NoError
}

fun validateUserUsername(username: String): DataFieldErrors {
    if(containsWhitespaces(username)) return DataFieldErrors.UserNameError("Contains whitespaces!")
    if(containsCapitalCharacters(username)) return DataFieldErrors.UserNameError("Contains capital letters!")
    if(containsSpecialCharacters(username)) return DataFieldErrors.UserNameError("Contains special characters!")

    return DataFieldErrors.NoError
}

fun validateUserNickname(nickname: String): DataFieldErrors {
    if(containsWhitespaces(nickname)) return DataFieldErrors.NickNameError("Contains whitespaces!")
    if(containsNumbers(nickname)) return DataFieldErrors.NickNameError("Contains numbers!")
    if(!nickname.matches(Regex("^[a-zA-Z]+\$"))) return DataFieldErrors.NickNameError("Nickname must contains only letters!")

    return DataFieldErrors.NoError
}

fun validateUserEmail(email: String): DataFieldErrors {
    if(!email.matches(Regex("^[A-Za-z0-9.]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"))) return DataFieldErrors.EmailAddressError("Invalid format!")

    return DataFieldErrors.NoError
}

fun validateUserPassword(password: String): DataFieldErrors {
    if(password.length < Constants.MIN_USER_PASSWORD_LENGTH) return DataFieldErrors.PasswordError("Password is too short!")
    if(password.length > Constants.MAX_USER_PASSWORD_LENGTH) return DataFieldErrors.PasswordError("Password is too long!")
    if(containsWhitespaces(password)) return DataFieldErrors.PasswordError("Contains whitespaces!")

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
    if(containsSpecialCharacters(title)) return DataFieldErrors.JobTitleError("Contains special characters!")
    if(startsOrEnsWithWhitespace(title)) return DataFieldErrors.JobTitleError("Starts or ends witch whitespace!")
    if(!title.matches(Regex("^[a-z0-9A-Z\\s]+\$"))) return DataFieldErrors.JobTitleError("Invalid format!")

    return DataFieldErrors.NoError
}

fun validateJobDescription(description: String): DataFieldErrors{
    if(description.length > Constants.MAX_JOB_DESCRIPTION_LENGTH) return DataFieldErrors.DescriptionError("Title is too long!")
    if(startsOrEnsWithWhitespace(description)) return DataFieldErrors.DescriptionError("Starts or ends witch whitespace!")

    return DataFieldErrors.NoError
}

fun validatePasswordsMatch(password: String, passwordChange: String): DataFieldErrors{
    if(password != passwordChange) return DataFieldErrors.PasswordMismatchError("Passwords do not match!")

    return DataFieldErrors.NoError
}

private fun containsSpecialCharacters(text: String): Boolean {
    return text.matches(Regex("\\w*[^\\w\\s]+\\w*"))
}

private fun containsNumbers(text: String): Boolean {
    return text.matches(Regex(".*[0-9].*"))
}

private fun containsWhitespaces(text: String): Boolean {
    return text.matches(Regex(".*\\s.*"))
}

private fun containsCapitalCharacters(text: String): Boolean {
    return text.matches(Regex(".*[A-Z].*"))
}

private fun containsMoreThan3Words(text: String): Boolean {
    return text.matches(Regex("^(?:\\S+\\s+){3,}\\S+\$"))
}

private fun startsOrEnsWithWhitespace(text: String): Boolean {
    return text.matches(Regex("^\\s.*|.*\\s\$"))
}

sealed class DataFieldErrors(val message: String){
    object NoError: DataFieldErrors(message = "")
    class FullNameError(msg: String): DataFieldErrors(message = msg)
    class DescriptionError(msg: String): DataFieldErrors(message = msg)
    class UserNameError(msg: String): DataFieldErrors(message = msg)
    class NickNameError(msg: String): DataFieldErrors(message = msg)
    class EmailAddressError(msg: String): DataFieldErrors(message = msg)
    class PasswordError(msg: String): DataFieldErrors(message = msg)
    class PasswordMismatchError(msg: String): DataFieldErrors(message = msg)
    class ConnectionPinError(msg: String): DataFieldErrors(message = msg)
    class JobTitleError(msg: String): DataFieldErrors(message = msg)
}

fun DataFieldErrors.or(error: DataFieldErrors): DataFieldErrors {
    if(this !is DataFieldErrors.NoError) return this
    return error
}

fun Collection<DataFieldErrors>.containsType(errorType: Class<out DataFieldErrors>): Boolean {
    this.forEach { error ->
        if(errorType.isInstance(error)){
            return true
        }
    }

    return false
}

fun Collection<DataFieldErrors>.getErrorMessage(errorType: Class<out DataFieldErrors>): String {
    this.forEach { error ->
        if(errorType.isInstance(error)){
            return error.message
        }
    }

    return ""
}