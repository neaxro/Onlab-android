package hu.bme.aut.android.securityapp.constants

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.bme.aut.android.securityapp.R

fun validateUserFullName(fullname: String, @ApplicationContext context: Context): DataFieldErrors {
    if(fullname.isEmpty()) return DataFieldErrors.FullNameError(context.getString(R.string.validator_error_fullname_is_empty))
    if(fullname.length > Constants.MAX_USER_FULLNAME_LENGTH) return DataFieldErrors.FullNameError(context.getString(R.string.validator_error_fullname_is_too_long))
    if(containsNumbers(fullname)) return DataFieldErrors.FullNameError(context.getString(R.string.validator_error_contains_numbers))
    if(containsSpecialCharacters(fullname)) return DataFieldErrors.FullNameError(context.getString(R.string.validator_error_contains_special_characters))
    if(startsOrEnsWithWhitespace(fullname)) return DataFieldErrors.FullNameError(context.getString(R.string.validator_error_starts_or_ends_witch_whitespace))
    if(containsMoreThan3Words(fullname)) return DataFieldErrors.FullNameError(context.getString(R.string.validator_error_contains_more_than_3_words))
    if(!fullname.matches(Regex("^[A-Za-z]+(?: [A-Za-z]+){0,2}\$"))) return DataFieldErrors.FullNameError(context.getString(R.string.validator_error_invalid_characters))

    return DataFieldErrors.NoError
}

fun validateUserUsername(username: String, @ApplicationContext context: Context): DataFieldErrors {
    if(containsWhitespaces(username)) return DataFieldErrors.UserNameError(context.getString(R.string.validator_error_starts_or_ends_witch_whitespace))
    if(containsCapitalCharacters(username)) return DataFieldErrors.UserNameError(context.getString(R.string.validator_error_contains_capital_letters))
    if(containsSpecialCharacters(username)) return DataFieldErrors.UserNameError(context.getString(R.string.validator_error_contains_special_characters))

    return DataFieldErrors.NoError
}

fun validateUserNickname(nickname: String, @ApplicationContext context: Context): DataFieldErrors {
    if(containsWhitespaces(nickname)) return DataFieldErrors.NickNameError(context.getString(R.string.validator_error_starts_or_ends_witch_whitespace))
    if(containsNumbers(nickname)) return DataFieldErrors.NickNameError(context.getString(R.string.validator_error_contains_numbers))
    if(!nickname.matches(Regex("^[a-zA-Z]+\$"))) return DataFieldErrors.NickNameError(context.getString(R.string.validator_error_nickname_must_contains_only_letters))

    return DataFieldErrors.NoError
}

fun validateUserEmail(email: String, @ApplicationContext context: Context): DataFieldErrors {
    if(!email.matches(Regex("^[A-Za-z0-9.]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"))) return DataFieldErrors.EmailAddressError(context.getString(R.string.validator_error_invalid_format))

    return DataFieldErrors.NoError
}

fun validateUserPassword(password: String, @ApplicationContext context: Context): DataFieldErrors {
    if(password.length < Constants.MIN_USER_PASSWORD_LENGTH) return DataFieldErrors.PasswordError(context.getString(R.string.validator_error_password_is_too_short))
    if(password.length > Constants.MAX_USER_PASSWORD_LENGTH) return DataFieldErrors.PasswordError(context.getString(R.string.validator_error_password_is_too_long))
    if(containsWhitespaces(password)) return DataFieldErrors.PasswordError(context.getString(R.string.validator_error_starts_or_ends_witch_whitespace))

    return DataFieldErrors.NoError
}

fun validateConnectionPin(pin: String, numberOfDigits: Int, @ApplicationContext context: Context): DataFieldErrors {
    if(pin.length != numberOfDigits) return DataFieldErrors.ConnectionPinError(context.getString(R.string.validator_error_pin_must_be_long, numberOfDigits.toString()))
    if(!pin.matches(Regex("^[A-Z0-9]+\$"))) return DataFieldErrors.ConnectionPinError(context.getString(R.string.validator_error_invalid_format))

    return DataFieldErrors.NoError
}

fun validateJobTitle(title: String, @ApplicationContext context: Context): DataFieldErrors{
    if(title.isEmpty()) return DataFieldErrors.JobTitleError(context.getString(R.string.validator_error_title_is_empty))
    if(title.length > Constants.MAX_JOB_TITLE_LENGTH) return DataFieldErrors.JobTitleError(context.getString(R.string.validator_error_title_is_too_long))
    if(containsSpecialCharacters(title)) return DataFieldErrors.JobTitleError(context.getString(R.string.validator_error_contains_special_characters))
    if(startsOrEnsWithWhitespace(title)) return DataFieldErrors.JobTitleError(context.getString(R.string.validator_error_starts_or_ends_witch_whitespace))
    if(!title.matches(Regex("^[a-z0-9A-Z\\s]+\$"))) return DataFieldErrors.JobTitleError(context.getString(R.string.validator_error_invalid_format))

    return DataFieldErrors.NoError
}

fun validateJobDescription(description: String, @ApplicationContext context: Context): DataFieldErrors{
    if(description.length > Constants.MAX_JOB_DESCRIPTION_LENGTH) return DataFieldErrors.DescriptionError(context.getString(R.string.validator_error_title_is_too_long))
    if(startsOrEnsWithWhitespace(description)) return DataFieldErrors.DescriptionError(context.getString(R.string.validator_error_starts_or_ends_witch_whitespace))

    return DataFieldErrors.NoError
}

fun validatePasswordsMatch(password: String, passwordChange: String, @ApplicationContext context: Context): DataFieldErrors{
    if(password != passwordChange) return DataFieldErrors.PasswordMismatchError(context.getString(R.string.validator_error_passwords_do_not_match))

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