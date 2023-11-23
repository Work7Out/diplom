package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.CONTENT
import diplom.gorinych.domain.utils.DATE_FEEDBACK
import diplom.gorinych.domain.utils.FEEDBACK
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.ID_HOUSE
import diplom.gorinych.domain.utils.ID_USER
import diplom.gorinych.domain.utils.IS_BLOCKED
import diplom.gorinych.domain.utils.RANG
import diplom.gorinych.domain.utils.formatLocalDateRu
import java.time.LocalDate

@Entity(tableName = FEEDBACK)
data class FeedBackEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = ID_USER)
    val idUser: Int,
    @ColumnInfo(name = ID_HOUSE)
    val idHouse: Int,
    @ColumnInfo(name = IS_BLOCKED)
    val isBlocked: Boolean,
    @ColumnInfo(name = CONTENT)
    val content: Boolean,
    @ColumnInfo(name = RANG)
    val rang: Int,
    @ColumnInfo(name = DATE_FEEDBACK)
    val dateFeedback: String = LocalDate.now().formatLocalDateRu(),
)
