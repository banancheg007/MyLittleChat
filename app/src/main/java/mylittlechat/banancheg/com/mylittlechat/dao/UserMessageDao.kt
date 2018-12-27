package mylittlechat.banancheg.com.mylittlechat.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import mylittlechat.banancheg.com.mylittlechat.UserMessage

@Dao
interface UserMessageDao {

    @get:Query("SELECT * FROM message_table ORDER BY id DESC")
    val allMessages: LiveData<List<UserMessage>>

    @Insert
    fun insert(message: UserMessage)

    @Update
    fun update(message: UserMessage)

    @Delete
    fun delete(message: UserMessage)
}