package diplom.gorinych.ui.presentation.admin.news

import diplom.gorinych.domain.model.Note

sealed class NewsScreenEvent {
    class AddNote(val title: String, val content: String) : NewsScreenEvent()
    class DeleteNote(val note: Note) : NewsScreenEvent()
    class UpdateNote(val title: String, val content: String, val id: Int) : NewsScreenEvent()
    object Exit : NewsScreenEvent()
}