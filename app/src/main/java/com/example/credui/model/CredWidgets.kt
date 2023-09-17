package com.example.credui.model

import com.example.credui.stackframework.model.Widget

data class AmountPickerWidget(
    val minAmount: Int,
    val maxAmount: Int,
    override val type: String,
) : Widget

data class BankPickerWidget(
    val bankList: List<Bank>,
    override val type: String,
) : Widget

data class Bank(
    val accountNumber: String,
    val name: String,
    val logo: String,
)


