package com.example.credui.ui.viewholders

import com.example.credui.Constants
import com.example.credui.stackframework.model.WidgetViewHolder

object ViewHolderProvider {


    fun getViewHolder(widgetType: String): WidgetViewHolder {
        return when (widgetType) {
            Constants.AMOUNT_PICKER_WIDGET -> AmountWidgetViewHolder()
            else -> BankSelectorViewHolder()
        }
    }
}