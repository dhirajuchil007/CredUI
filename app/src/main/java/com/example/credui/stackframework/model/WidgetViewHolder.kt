package com.example.credui.stackframework.model

import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.credui.model.WidgetResponse

interface WidgetViewHolder {
    fun initLayout(layoutInflater: LayoutInflater)

    fun setView(parent: FrameLayout, data: Widget)

    fun getResponse(): WidgetResponse
}