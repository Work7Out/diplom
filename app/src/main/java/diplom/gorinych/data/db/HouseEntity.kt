package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.DESCRIPTION
import diplom.gorinych.domain.utils.HOUSE
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.NAME
import diplom.gorinych.domain.utils.PATH_IMAGE
import diplom.gorinych.domain.utils.PRICE

@Entity(tableName = HOUSE)
data class HouseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = DESCRIPTION)
    val description: String,
    @ColumnInfo(name = PRICE)
    val price: Double,
    @ColumnInfo(name = PATH_IMAGE)
    val image: Int,
)
