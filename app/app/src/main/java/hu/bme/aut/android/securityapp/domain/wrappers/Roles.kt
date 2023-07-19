package hu.bme.aut.android.securityapp.domain.wrappers

sealed class Roles(val title: String) {
    class Owner(): Roles("Owner")
    class Admin(): Roles("Admin")
    class User(): Roles("User")
    class Anonymous(): Roles("Anonymous")
}