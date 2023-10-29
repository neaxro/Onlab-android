package hu.bme.aut.android.securityapp.data.repository

import android.app.Application
import hu.bme.aut.android.securityapp.R
import hu.bme.aut.android.securityapp.data.model.role.Role
import hu.bme.aut.android.securityapp.data.remote.RoleApi
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

class RoleRepositoryImpl constructor(
    private val api: RoleApi,
    private val app: Application,
) : RoleRepository {

    private val context = app.applicationContext

    override suspend fun getAllRoles(): Resource<List<Role>> {
        val connection = try{
            val result = api.getAllRoles()

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_queried_all_roles), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun getAllChoosableRoles(): Resource<List<Role>> {
        val connection = try{
            val result = api.getAllChoosableRoles()

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_get_all_choosable_roles), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }

    override suspend fun getRoleById(roleId: Int): Resource<Role> {
        val connection = try{
            val result = api.getRoleById(roleId = roleId)

            val data = if(result.isSuccessful && result.code() == 200){
                Resource.Success(message = context.getString(R.string.repository_message_successfully_queried_role), data = result.body()!!)
            }
            else{
                Resource.Error(message = result.errorBody()!!.string())
            }

            data
        } catch(e: Exception){
            Resource.Error(
                context.getString(
                    R.string.repository_message_network_error_occurred,
                    e.message
                ))
        }

        return connection
    }
}