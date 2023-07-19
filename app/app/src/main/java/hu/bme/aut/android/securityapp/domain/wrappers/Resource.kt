package hu.bme.aut.android.securityapp.domain.wrappers

sealed class Resource<T>(val message: String?, val data: T?) {
    class Success<T>(data: T, message: String? = null) : Resource<T>(message, data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(message, data)
}