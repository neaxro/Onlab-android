package hu.bme.aut.android.securityapp.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        Log.d("NETWORK", "[SENDING] Headers: ${request.headers()}, URL: ${request.url()}, Body: ${request.body()}")

        val response: Response = chain.proceed(request)

        Log.d("NETWORK", "[RECEIVING] Headers: ${response.headers()}, URL: ${response.request().url()}, Body: ${request.body()}")

        return response
    }
}