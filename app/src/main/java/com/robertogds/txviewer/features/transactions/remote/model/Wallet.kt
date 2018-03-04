package com.robertogds.txviewer.features.transactions.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Roberto.Gil on 03/03/2018
 */

data class Wallet (
        @SerializedName("n_tx") val numberTx: Int,
        @SerializedName("final_balance") val finalBalance: Long
)