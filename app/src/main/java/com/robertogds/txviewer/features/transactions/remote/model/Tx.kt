package com.robertogds.txviewer.features.transactions.remote.model

/**
 * Created by Roberto.Gil on 03/03/2018
 */
data class Tx(
        val result: Long,
        val time: Long,
        val hash: String,
        val fee: Long,
        val out: ArrayList<Output>
)

data class Output (
        var value: Long,
        var address: String
)