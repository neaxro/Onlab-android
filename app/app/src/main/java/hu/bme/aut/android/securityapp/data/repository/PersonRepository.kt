package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.people.Person
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface PersonRepository {
    suspend fun getPerson(personId: Int): Resource<Person>
}