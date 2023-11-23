package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.IS_BLOCKED
import diplom.gorinych.domain.utils.NAME
import diplom.gorinych.domain.utils.PASSWORD
import diplom.gorinych.domain.utils.ROLE
import diplom.gorinych.domain.utils.USERS

@Entity(tableName = USERS)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int =0,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = PASSWORD)
    val password: String,
    @ColumnInfo(name = ROLE)
    val role: String,
    @ColumnInfo(name = IS_BLOCKED)
    val isBlocked: Boolean,
)
