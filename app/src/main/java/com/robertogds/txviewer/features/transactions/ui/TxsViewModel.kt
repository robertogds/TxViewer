package com.robertogds.txviewer.features.transactions.ui

/**
 * Created by Roberto.Gil on 03/03/2018
 */

import com.robertogds.txviewer.features.transactions.remote.ApiContract
import com.robertogds.txviewer.features.transactions.remote.model.TxApiResponse
import com.robertogds.txviewer.features.transactions.repository.TxRepository
import com.robertogds.txviewer.util.SchedulerProvider


import io.reactivex.Single

class TxsViewModel(private val txRepository: TxRepository, private val schedulerProvider: SchedulerProvider) {

    fun showDataFromApi(): Single<TxApiResponse> = txRepository.getDataFromApi(ApiContract.XPUB)
            .compose(schedulerProvider.getSchedulersForSingle())

}