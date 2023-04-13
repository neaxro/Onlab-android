package hu.bme.aut.android.securityapp.data.model.job

import hu.bme.aut.android.securityapp.data.model.people.Person

data class DetailedJob(
    val id: Int,
    val title: String,
    val description: String,
    val pin: String,
    val owner: Person,
)