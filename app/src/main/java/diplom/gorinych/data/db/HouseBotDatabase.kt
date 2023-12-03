package diplom.gorinych.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        AddonEntity::class,
        CallHistory::class,
        FeedBackEntity::class,
        HistoryEntity::class,
        HouseEntity::class,
        NoteEntity::class,
        UserEntity::class,
        PromoEntity::class
    ],
    version = 1
)
abstract class HouseBotDatabase : RoomDatabase() {

    abstract val houseDao: HouseDao

}