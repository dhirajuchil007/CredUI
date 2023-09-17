package com.example.credui.model

interface WidgetResponse {
    val type: String
}


data class AmountPickerResponse(val selectedAmount: Int, override val type: String) : WidgetResponse

data class BankPickerResponse(val selectedBank: Bank, override val type: String) :
    WidgetResponse