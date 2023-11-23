package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.CONFIRM_RESERVATION
import diplom.gorinych.domain.utils.DATA_BEGIN
import diplom.gorinych.domain.utils.DATA_END
import diplom.gorinych.domain.utils.HISTORY
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.ID_HOUSE
import diplom.gorinych.domain.utils.ID_USER
import diplom.gorinych.domain.utils.RESERVATION

@Entity(tableName = HISTORY)
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = ID_USER)
    val idUser: Int,
    @ColumnInfo(name = ID_HOUSE)
    val idHouse: Int,
    @ColumnInfo(name = DATA_BEGIN)
    val dateBegin: String,
    @ColumnInfo(name = DATA_END)
    val dateEnd: String,
    @ColumnInfo(name = RESERVATION)
    val reservation: String,
    @ColumnInfo(name = CONFIRM_RESERVATION)
    val confirmReservation: String,
)
