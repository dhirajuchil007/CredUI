package com.example.credui.stackframework.model

sealed interface TapAction

data class SelectEMIAction(val emiAmount: Double) : TapAction
data class SelectBankAction(val id: String) : TapAction