package mobi.samdroid.bootcamp.base.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mobi.samdroid.bootcamp.base.SUser

@Dao
interface SUserDao {
    @Insert
    suspend fun insert(user: SUser)

    @Query("SELECT * FROM USERS WHERE username = :username AND password = :password")
    suspend fun getUserByCredentials(username: String, password: String): SUser?

    @Query("SELECT * FROM USERS WHERE username = :username")
    suspend fun getUserByUsername(username: String): SUser?

    @Query("SELECT * FROM USERS")
    suspend fun getAllUsers(): List<SUser>
}