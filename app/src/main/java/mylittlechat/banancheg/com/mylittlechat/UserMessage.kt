package mylittlechat.banancheg.com.mylittlechat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "message_table")
class UserMessage(
    @field:ColumnInfo(name = "user_id")
    var userId: Int,

    @field:ColumnInfo(name = "text")
    var text: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

