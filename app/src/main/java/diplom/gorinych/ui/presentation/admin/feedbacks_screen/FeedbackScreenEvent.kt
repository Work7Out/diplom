package diplom.gorinych.ui.presentation.admin.feedbacks_screen

import diplom.gorinych.domain.model.Feedback

sealed class FeedbackScreenEvent{
    class OnChangeStatusBlockFeedback(val feedback: Feedback): FeedbackScreenEvent()
}
