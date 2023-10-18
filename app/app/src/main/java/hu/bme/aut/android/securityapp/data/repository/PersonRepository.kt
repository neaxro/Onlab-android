package hu.bme.aut.android.securityapp.data.repository

import android.content.Context
import android.net.Uri
import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.data.model.people.PersonDefault
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface PersonRepository {
    suspend fun getPerson(personId: Int): Resource<Person>

    suspend fun updatePerson(personId: Int, person: PersonDefault): Resource<PersonDefault>

    suspend fun uploadProfilePicture(personId: Int, imageUri: Uri, context: Context): Resource<Unit>
}