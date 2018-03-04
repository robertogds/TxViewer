package com.robertogds.txviewer.features.transactions.repository

/**
 * Created by Roberto.Gil on 03/03/2018
 */
import com.robertogds.txviewer.features.transactions.remote.ApiService
import com.robertogds.txviewer.features.transactions.remote.model.TxApiResponse
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TxRepository @Inject constructor(private val apiService: ApiService) {

    fun getDataFromApi(active: String): Single<TxApiResponse> = apiService.getJsonResponse(active)

}