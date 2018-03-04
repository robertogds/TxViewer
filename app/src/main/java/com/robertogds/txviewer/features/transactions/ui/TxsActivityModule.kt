package com.robertogds.txviewer.features.transactions.ui

/**
 * Created by Roberto.Gil on 03/03/2018
 */
import com.robertogds.txviewer.features.transactions.repository.TxRepository
import com.robertogds.txviewer.util.SchedulerProvider
import dagger.Module
import dagger.Provides


@Module
class TxsActivityModule {

    @Provides
    fun provideViewModel(txRepository: TxRepository, schedulerProvider: SchedulerProvider) = TxsViewModel(txRepository, schedulerProvider)
}