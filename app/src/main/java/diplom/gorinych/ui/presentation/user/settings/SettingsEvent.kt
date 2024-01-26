package diplom.gorinych.ui.presentation.user.settings


sealed class SettingsEvent {
    class OnChangePassword(
        val oldPassword: String,
        val password: String,
        val repeatPassword: String) : SettingsEvent()
    object OnSendCall : SettingsEvent()
    object Exit : SettingsEvent()
}