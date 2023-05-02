package hu.bme.aut.android.securityapp.constants

import android.util.Log
import com.auth0.android.jwt.JWT
import hu.bme.aut.android.securityapp.domain.wrappers.Roles

object LoggedPerson {
    var ID: Int = 0
    var CURRENT_JOB_ID = 0

    var TOKEN: String = ""
        set(value){
            jwt = JWT(value)
            field = value
        }

    lateinit var jwt: JWT

    fun getRole(): Roles{
        val claim = jwt.getClaim("http://schemas.microsoft.com/ws/2008/06/identity/claims/role").asString()
        Log.d("JWT", "[ROLE] $claim")

        return when(claim){
            "Owner" -> {
                Roles.Owner()
            }
            "Admin" -> {
                Roles.Admin()
            }
            "User" -> {
                Roles.User()
            }
            else -> {
                Roles.Anonymous()
            }
        }
    }
}