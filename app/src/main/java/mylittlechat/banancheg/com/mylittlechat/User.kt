package mylittlechat.banancheg.com.mylittlechat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
class User(@field:ColumnInfo(name = "user_name")
           val userName: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}