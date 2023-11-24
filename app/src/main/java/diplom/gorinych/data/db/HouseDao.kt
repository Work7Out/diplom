package diplom.gorinych.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import diplom.gorinych.domain.utils.FEEDBACK
import diplom.gorinych.domain.utils.HOUSE
import diplom.gorinych.domain.utils.USERS

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

    @Update
    suspend fun updateUserEntity(userEntity: UserEntity)

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

}