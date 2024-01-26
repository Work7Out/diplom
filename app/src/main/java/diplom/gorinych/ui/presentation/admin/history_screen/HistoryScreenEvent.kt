package diplom.gorinych.ui.presentation.admin.history_screen

import diplom.gorinych.domain.model.Feedback
import diplom.gorinych.domain.model.Reserve
import diplom.gorinych.ui.presentation.admin.addons.AddonScreenEvent

sealed class HistoryScreenEvent{
    class OnConfirmReserve(val reserve: Reserve, val status:String): HistoryScreenEvent()
    class OnChangeStatusBlockFeedback(val feedback: Feedback, val message: String): HistoryScreenEvent()
    class OnChangeHistoryState(val historyState: HistoryState): HistoryScreenEvent()
    object Exit : HistoryScreenEvent()
}
