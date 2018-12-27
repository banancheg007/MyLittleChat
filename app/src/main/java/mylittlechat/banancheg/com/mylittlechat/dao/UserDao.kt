package mylittlechat.banancheg.com.mylittlechat.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import mylittlechat.banancheg.com.mylittlechat.User

@Dao
interface UserDao {

    @get:Query("SELECT * FROM user_table ORDER BY id DESC")
    val allUsers: LiveData<List<User>>

    @Insert
    fun insert(user: User)


}