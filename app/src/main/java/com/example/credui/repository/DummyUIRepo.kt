package com.example.credui.repository

import com.example.credui.Constants
import com.example.credui.model.AmountPickerWidget
import com.example.credui.model.Bank
import com.example.credui.model.BankPickerWidget
import com.example.credui.stackframework.model.CollapsedHeaderItem
import com.example.credui.stackframework.model.CardUIData
import javax.inject.Inject

class DummyUIRepo @Inject constructor() : UIRepo {


    override suspend fun getCardUI(): List<CardUIData> {
        return listOf<CardUIData>(
            CardUIData(
                "Dhiraj, How much do you need?",
                "Move the dial to set any amount you need up to £1000000",
                listOf(CollapsedHeaderItem("credit amount", "$10000000")),
                "Proceed to EMI election", AmountPickerWidget(
                    1000000,
                    5000000, Constants.AMOUNT_PICKER_WIDGET
                )
            ),
            CardUIData(
                "Where should we send the money?",
                "amount will be transferred to the back account," +
                        " emi will also be debited from this account",
                listOf(CollapsedHeaderItem("credit amount", "$10000000")),
                "Submit", BankPickerWidget(
                    listOf(
                        Bank("123456789", "HDFC", "https://placehold.co/200"),
                        Bank("123456789", "Paytm", "https://placehold.co/200")
                    ),
                    Constants.BANK_PICKER_WIDGET
                )
            )
        )
    }
}