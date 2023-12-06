package diplom.gorinych.ui.presentation.admin.news

import diplom.gorinych.domain.model.Note

data class NewsScreenState(
    val message:String? = null,
    val idUser: Int = -1,
    val countNewReserves: Int = 0,
    val news: List<Note> = emptyList()
)