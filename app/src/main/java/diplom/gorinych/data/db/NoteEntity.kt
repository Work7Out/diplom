package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.CONTENT
import diplom.gorinych.domain.utils.DATE_CREATE
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.NEWS
import diplom.gorinych.domain.utils.TITLE

@Entity(tableName = NEWS)
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = TITLE)
    val title: String,
    @ColumnInfo(name = CONTENT)
    val content: String,
    @ColumnInfo(name = DATE_CREATE)
    val dateCreate: String //LocalDate.now().formatLocalDateRu(),
)
