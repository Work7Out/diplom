package diplom.gorinych.ui.presentation.user.history_user

import diplom.gorinych.domain.model.Reserve

sealed class HistoryUserEvent {
    class OnDeleteReserve(val reserve: Reserve):HistoryUserEvent()
    object OnSendCall: HistoryUserEvent()
}
