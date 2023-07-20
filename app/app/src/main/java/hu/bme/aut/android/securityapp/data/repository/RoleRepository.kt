package hu.bme.aut.android.securityapp.data.repository

import hu.bme.aut.android.securityapp.data.model.role.Role
import hu.bme.aut.android.securityapp.domain.wrappers.Resource

interface RoleRepository {

    suspend fun getAllRoles(): Resource<List<Role>>

    suspend fun getAllChoosableRoles(): Resource<List<Role>>

    suspend fun getRoleById(roleId: Int): Resource<Role>
}