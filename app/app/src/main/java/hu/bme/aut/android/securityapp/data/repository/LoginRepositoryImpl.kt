package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.LoginData
import hu.bme.aut.android.securityapp.data.model.Person
import hu.bme.aut.android.securityapp.data.model.RegisterData
import hu.bme.aut.android.securityapp.data.remote.LoginApi
import hu.bme.aut.android.securityapp.domain.repository.LoginRepository

class LoginRepositoryImpl(
    val api: LoginApi
): LoginRepository {

    override suspend fun GetAllPerson(): List<Person> {
        val response = api.getAllPeople()

        return listOf(Person(1,"Nemes Axel", "axel", "axel", "asd@gmail.com", ""))
    }

    override suspend fun LoginPerson(loginData: LoginData): Person? {
        val response = api.login(loginData)

        if(response.isSuccessful){
            with(response.body()){
                val loggedIn = this?.let { Person(it.id, it.fullName, it.username, it.nickname, it.email, it.profilePicture) }
                return loggedIn
            }

        }
        else{
            return null
        }
    }

    override suspend fun RegisterPerson(registerData: RegisterData): Person? {
        val response = api.register((registerData))

        if(response.isSuccessful){
            with(response.body()){
                val registered = this?.let { Person(it.id, it.fullName, it.username, it.nickname, it.email, it.profilePicture) }
                return registered
            }

        }
        else{
            return null
        }
    }
}