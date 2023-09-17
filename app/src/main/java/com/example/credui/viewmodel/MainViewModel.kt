package com.example.credui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.credui.stackframework.model.CardUIData
import com.example.credui.usecases.GetCardUIUseCase
import com.example.credui.utility.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getCardUIUseCase: GetCardUIUseCase) :
    ViewModel() {

    private val _Card_uiData = MutableLiveData<NetworkResult<List<CardUIData>>>(NetworkResult.Loading())
    val uiInfo = _Card_uiData


    fun getUIInfo() {
        _Card_uiData.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                getCardUIUseCase.invoke().collect {
                    _Card_uiData.value = it
                }
            } catch (e: Exception) {
                _Card_uiData.value = NetworkResult.Error(e.message ?: "Unknown error")
            }
        }
    }
}