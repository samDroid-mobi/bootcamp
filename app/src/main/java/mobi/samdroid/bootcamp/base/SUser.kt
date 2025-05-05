package mobi.samdroid.bootcamp.base

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "USERS")
class SUser(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    var username: String = "",
    var mobileNumber: String = "",
    var password: String = "",
)