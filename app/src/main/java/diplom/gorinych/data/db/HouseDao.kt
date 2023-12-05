package diplom.gorinych.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import diplom.gorinych.domain.utils.ADDONS
import diplom.gorinych.domain.utils.FEEDBACK
import diplom.gorinych.domain.utils.HISTORY
import diplom.gorinych.domain.utils.HOUSE
import diplom.gorinych.domain.utils.NEWS
import diplom.gorinych.domain.utils.PROMOS
import diplom.gorinych.domain.utils.USERS
import diplom.gorinych.domain.utils.WAITING_CONFIRM
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    //UserEntity
    @Query("SELECT * FROM $USERS WHERE name = :login AND password=:password")
    suspend fun getUserByLoginAndPassword(
        login: String,
        password: String
    ): UserEntity?

    @Query("SELECT * FROM $USERS WHERE id = :userId")
    suspend fun getUserById(userId: Int): UserEntity

    @Query("SELECT * FROM $USERS")
    suspend fun getAllUsers(): List<UserEntity>

    @Update
    suspend fun updateUserEntity(userEntity: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    //HouseEntity
    @Query("SELECT * FROM $HOUSE")
    suspend fun getAllHouses() : List<HouseEntity>

    @Query("SELECT * FROM $HOUSE WHERE id = :houseId")
    suspend fun getHouseById(
        houseId:Int
    ) : HouseEntity




    //FeedBackEntity
    @Query("SELECT * FROM $FEEDBACK WHERE ID_HOUSE = :houseId")
    suspend fun getFeedBackByHouse(
        houseId:Int
    ) : List<FeedBackEntity>

    @Query("SELECT * FROM $FEEDBACK")
    fun getAllFeedBacks() : Flow<List<FeedBackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedBackEntity: FeedBackEntity)

    @Update
    suspend fun updateFeedback(feedBackEntity: FeedBackEntity)


    //HISTORY
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReserve(historyEntity: HistoryEntity)

    @Update
    suspend fun updateHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM $HISTORY WHERE ID_USER = :userId")
    suspend fun getHistoryByUser(
        userId:Int
    ) : List<HistoryEntity>

    @Query("SELECT * FROM $HISTORY")
    fun getAllHistory() : Flow<List<HistoryEntity>>

    @Query("SELECT * FROM $HISTORY WHERE CONFIRM_RESERVATION = :status")
    fun getHistoryNoConfirmStatus(status: String = WAITING_CONFIRM) : Flow<List<HistoryEntity>>

    @Delete
    suspend fun deleteReserve(historyEntity: HistoryEntity)

    //Addons
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddon(addonEntity: AddonEntity)

    @Query("SELECT * FROM $ADDONS")
    fun getAllAddons() : Flow<List<AddonEntity>>

    //Promos
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPromo(promoEntity: PromoEntity)

    @Query("SELECT * FROM $PROMOS")
    fun getAllPromos() : Flow<List<PromoEntity>>

    //News
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(note: NoteEntity)

    @Query("SELECT * FROM $NEWS")
    fun getAllNews() : Flow<List<NoteEntity>>

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

}