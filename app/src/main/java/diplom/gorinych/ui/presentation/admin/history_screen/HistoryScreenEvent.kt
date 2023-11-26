package diplom.gorinych.ui.presentation.admin.history_screen

import diplom.gorinych.domain.model.Reserve

sealed class HistoryScreenEvent{
    class OnConfirmReserve(val reserve: Reserve, val status:String): HistoryScreenEvent()
}
