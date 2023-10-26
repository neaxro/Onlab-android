package hu.bme.aut.android.securityapp.domain.wrappers

import android.content.Context
import hu.bme.aut.android.securityapp.MyApp
import hu.bme.aut.android.securityapp.R

sealed class Roles(
    val title: String,
    val context: Context
) {
    class Owner(context: Context = MyApp.applicationContext()): Roles(context.getString(R.string.wrappers_roles_owner), context)
    class Admin(context: Context = MyApp.applicationContext()): Roles(context.getString(R.string.wrappers_roles_admin), context)
    class User(context: Context = MyApp.applicationContext()): Roles(context.getString(R.string.wrappers_roles_user), context)
    class Anonymous(context: Context = MyApp.applicationContext()): Roles(context.getString(R.string.wrappers_roles_anonymous), context)
}