package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.CALL_HISTORY
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.NAME
import diplom.gorinych.domain.utils.PHONE

@Entity(tableName = CALL_HISTORY)
data class CallHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = PHONE)
    val phone: String,
)
