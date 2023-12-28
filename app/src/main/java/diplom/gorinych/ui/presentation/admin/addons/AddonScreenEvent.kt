package diplom.gorinych.ui.presentation.admin.addons

import diplom.gorinych.domain.model.Addon

sealed class AddonScreenEvent {
    class InsertPromo(val description: String, val value: Int) : AddonScreenEvent()
    class InsertAddon(val title: String, val price: Double) : AddonScreenEvent()
    class ChangeState(val addonState: AddonState) : AddonScreenEvent()
    class DeleteAddon(val addon: Addon) : AddonScreenEvent()
    class UpdateAddon(val title: String, val price: Double, val id: Int) : AddonScreenEvent()
    object Exit : AddonScreenEvent()
}