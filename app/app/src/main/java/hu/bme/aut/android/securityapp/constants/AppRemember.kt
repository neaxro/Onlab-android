package hu.bme.aut.android.securityapp.constants

import android.content.SharedPreferences
import hu.bme.aut.android.securityapp.data.model.people.LoginData

object AppRemember {

    private const val jobIdKey = "selected_job_id"
    private const val usernameKey = "username"
    private const val passwordKey = "password"

    fun rememberSelectedJobId(sharedPreferences: SharedPreferences, jobId: Int){
        val editor = sharedPreferences.edit()
        editor.putInt(jobIdKey, jobId)
        editor.apply()
    }

    fun forgetSelectedJobId(sharedPreferences: SharedPreferences){
        rememberSelectedJobId(sharedPreferences = sharedPreferences, jobId = 0)
    }

    fun getSelectedJobId(sharedPreferences: SharedPreferences): Int {
        return sharedPreferences.getInt(jobIdKey, 0)
    }

    fun rememberLoginData(sharedPreferences: SharedPreferences, username: String, password: String){
        val editor = sharedPreferences.edit()
        with(editor){
            putString(usernameKey, username)
            putString(passwordKey, password)
            apply()
        }
    }

    fun getLoginData(sharedPreferences: SharedPreferences): LoginData? {
        val username = sharedPreferences.getString(usernameKey, "")
        val password = sharedPreferences.getString(passwordKey, "")

        return if(username?.isNotEmpty() == true && password?.isNotEmpty() == true){
            LoginData(username = username, password = password)
        }
        else{
            null
        }
    }

    fun forgetLoginData(sharedPreferences: SharedPreferences){
        rememberLoginData(sharedPreferences = sharedPreferences, username = "", password = "")
    }
}