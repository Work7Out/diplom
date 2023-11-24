package diplom.gorinych.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import diplom.gorinych.domain.utils.USERS

@Dao
interface HouseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity)

    //UserEntity
    @Query("SELECT * FROM $USERS WHERE name = :login AND password=:password")
    suspend fun getUserByLoginAndPassword(
        login:String,
        password:String
    ): UserEntity?
}