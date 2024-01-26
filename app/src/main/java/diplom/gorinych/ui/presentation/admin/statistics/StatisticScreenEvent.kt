package diplom.gorinych.ui.presentation.admin.statistics



sealed class StatisticScreenEvent {
    class OnUpdatePhone (
        val id: Int,
        val name: String,
        val phone: String,
        val isResponse: Boolean) : StatisticScreenEvent()
    object Exit : StatisticScreenEvent()
}