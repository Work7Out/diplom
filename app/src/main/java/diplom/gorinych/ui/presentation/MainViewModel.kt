package diplom.gorinych.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import diplom.gorinych.data.db.HouseBotDatabase
import diplom.gorinych.data.db.HouseDao
import diplom.gorinych.data.db.NoteEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val db:HouseBotDatabase
): ViewModel() {
    private val houseDao = db.houseDao
    fun addNews() {
        viewModelScope.launch {
            houseDao.insertNote(
                NoteEntity(
                    title = "SDF",
                    content = "ssddff"
                )
            )
        }
    }
}