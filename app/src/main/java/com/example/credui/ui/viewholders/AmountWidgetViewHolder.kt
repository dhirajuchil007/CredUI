package com.example.credui.ui.viewholders

import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.SeekBar
import com.example.credui.Constants
import com.example.credui.databinding.AmountPickerWidgetBinding
import com.example.credui.model.AmountPickerResponse
import com.example.credui.model.AmountPickerWidget
import com.example.credui.model.WidgetResponse
import com.example.credui.stackframework.model.Widget
import com.example.credui.stackframework.model.WidgetViewHolder

class AmountWidgetViewHolder : WidgetViewHolder {

    lateinit var binding: AmountPickerWidgetBinding
    var selectedValue = 0

    override fun initLayout(layoutInflater: LayoutInflater) {
        binding = AmountPickerWidgetBinding.inflate(layoutInflater)
    }


    override fun setView(parent: FrameLayout, widgetData: Widget) {

        parent.addView(binding.root)

        val data = widgetData as AmountPickerWidget
        val interval = 1000
        binding.seekBar.max = (data.maxAmount - data.minAmount) / interval
        selectedValue = data.minAmount

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedValue = progress * interval + data.minAmount
                binding.txtCreditAmountValue.text =
                    "$$selectedValue"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //do nothing
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                //do nothing
            }

        })


    }

    override fun getResponse(): WidgetResponse = AmountPickerResponse(
        selectedAmount = selectedValue,
        type = Constants.AMOUNT_PICKER_WIDGET
    )
}