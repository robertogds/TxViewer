package com.robertogds.txviewer.features.transactions.remote.model

/**
 * Created by Roberto.Gil on 03/03/2018
 */

data class TxApiResponse(
       val wallet: Wallet,
       val txs: List<Tx>
)