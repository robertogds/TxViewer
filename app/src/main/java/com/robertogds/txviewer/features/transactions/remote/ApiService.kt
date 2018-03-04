package com.robertogds.txviewer.features.transactions.remote

/**
 * Created by Roberto.Gil on 03/03/2018
 */

import com.robertogds.txviewer.features.transactions.remote.model.TxApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET(ApiContract.ENDPOINT_MULTIADDR)
    fun getJsonResponse(
        @Query(ApiContract.PARAM_ACTIVE) active: String
    ): Single<TxApiResponse>
}