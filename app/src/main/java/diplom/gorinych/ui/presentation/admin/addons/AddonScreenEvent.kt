package diplom.gorinych.ui.presentation.admin.addons

sealed class AddonScreenEvent {
    class InsertPromo(val description: String, val value: Int) : AddonScreenEvent()
    class InsertAddon(val title: String, val price: Double) : AddonScreenEvent()
    class ChangeState(val addonState: AddonState) : AddonScreenEvent()
}