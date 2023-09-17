package com.example.credui.repository

import com.example.credui.stackframework.model.CardUIData

interface UIRepo {
    suspend fun getCardUI(): List<CardUIData>
}