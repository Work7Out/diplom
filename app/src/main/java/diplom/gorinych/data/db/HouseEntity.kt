package diplom.gorinych.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import diplom.gorinych.domain.utils.COUNT_ROOMS
import diplom.gorinych.domain.utils.DESCRIPTION
import diplom.gorinych.domain.utils.HOUSE
import diplom.gorinych.domain.utils.ID
import diplom.gorinych.domain.utils.IS_CLEANING_SERVICE
import diplom.gorinych.domain.utils.IS_CONDITIONER
import diplom.gorinych.domain.utils.IS_REFRIGERATOR
import diplom.gorinych.domain.utils.IS_SWIMMING_POOL
import diplom.gorinych.domain.utils.IS_WIFI
import diplom.gorinych.domain.utils.IS_YOGA
import diplom.gorinych.domain.utils.LATITUDE
import diplom.gorinych.domain.utils.LONGITUDE
import diplom.gorinych.domain.utils.MIDDLE_RANG
import diplom.gorinych.domain.utils.NAME
import diplom.gorinych.domain.utils.PRICE
import diplom.gorinych.domain.utils.TYPE

@Entity(tableName = HOUSE)
data class HouseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Int = 0,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = TYPE)
    val type: String,
    @ColumnInfo(name = MIDDLE_RANG)
    val middleRang: Double,
    @ColumnInfo(name = DESCRIPTION)
    val description: String,
    @ColumnInfo(name = IS_CONDITIONER)
    val isConditioner: Boolean,
    @ColumnInfo(name = IS_REFRIGERATOR)
    val isRefrigerator: Boolean,
    @ColumnInfo(name = IS_SWIMMING_POOL)
    val isSwimmingPool: Boolean,
    @ColumnInfo(name = IS_CLEANING_SERVICE)
    val isCleaningService: Boolean,
    @ColumnInfo(name = IS_WIFI)
    val isWifi: Boolean,
    @ColumnInfo(name = IS_YOGA)
    val isYoga: Boolean,
    @ColumnInfo(name = LONGITUDE)
    val longitude: Double,
    @ColumnInfo(name = LATITUDE)
    val latitude: Double,
    @ColumnInfo(name = PRICE)
    val price: Double,
    @ColumnInfo(name = COUNT_ROOMS)
    val countRooms: Int,
)
