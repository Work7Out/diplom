package diplom.gorinych.ui.presentation.admin.feedbacks_screen

import diplom.gorinych.domain.model.Feedback

data class FeedbacksScreenState(
    val feedbacks: List<Feedback> = emptyList(),
    val message:String? = null,
    val idUser: Int = -1,
    val countNewReserves: Int = 0
)
