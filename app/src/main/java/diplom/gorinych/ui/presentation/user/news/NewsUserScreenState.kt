package diplom.gorinych.ui.presentation.user.news

import diplom.gorinych.domain.model.Note
import diplom.gorinych.domain.model.User

data class NewsUserScreenState(
    val message:String? = null,
    val news: List<Note> = emptyList(),
    val user: User? = null,
    val isLoading: Boolean = true,
)
