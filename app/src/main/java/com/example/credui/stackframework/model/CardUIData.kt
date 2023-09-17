package com.example.credui.stackframework.model

data class CardUIData(
    val heading: String, val subHeading: String,
    val collapsedHeaderItems: List<CollapsedHeaderItem>,
    val mainActionText: String,
    val widget: Widget?,
)


data class CollapsedHeaderItem(val label: String, val value: String)













