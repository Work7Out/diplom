package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.ADDONS
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.ID_HOUSE
import diplom.gorinych.domain.utils.PRICE
import diplom.gorinych.domain.utils.TITLE

@Entity(tableName = ADDONS)
data class AddonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = TITLE)
    val title: String,
    @ColumnInfo(name = PRICE)
    val price: Double,
)
