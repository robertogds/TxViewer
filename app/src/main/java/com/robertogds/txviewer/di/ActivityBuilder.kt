package com.robertogds.txviewer.di

import com.robertogds.txviewer.features.transactions.ui.TxsActivity
import com.robertogds.txviewer.features.transactions.ui.TxsActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Roberto.Gil on 03/03/2018
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = arrayOf(TxsActivityModule::class))
    abstract fun bindMainActivity(): TxsActivity
}