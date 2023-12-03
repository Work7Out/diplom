package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.DESCRIPTION
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.IS_ACTIVE
import diplom.gorinych.domain.utils.PROMOS
import diplom.gorinych.domain.utils.VALUE_DISCOUNT

@Entity(tableName = PROMOS)
data class PromoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = VALUE_DISCOUNT)
    val valueDiscount: Int,
    @ColumnInfo(name = DESCRIPTION)
    val description: String,
    @ColumnInfo(name = IS_ACTIVE)
    val isActive: Boolean,
)
