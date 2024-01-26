package diplom.gorinych.ui.presentation.admin.addons

sealed interface AddonState {
    object PromoState:AddonState
    object AdditionState:AddonState
}