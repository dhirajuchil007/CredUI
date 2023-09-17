package com.example.credui.usecases

import com.example.credui.stackframework.model.CardUIData
import com.example.credui.repository.UIRepo
import com.example.credui.utility.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class GetCardUIUseCase @Inject constructor(val uiRepo: UIRepo) {

    operator fun invoke(): Flow<NetworkResult<List<CardUIData>>> =
        flow {
            emit(NetworkResult.Loading())
            try {
                val uiInfo = uiRepo.getCardUI()
                emit(NetworkResult.Success(uiInfo))
            } catch (e: Exception) {
                emit(NetworkResult.Error(e.message ?: "Unknown error"))
            }
        }

}