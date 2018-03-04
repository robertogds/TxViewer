package com.robertogds.txviewer.features.transactions.remote

/**
 * Created by Roberto.Gil on 03/03/2018
 */
class ApiContract {
    companion object {
        const val BASE_URL = "https://blockchain.info/"
        const val ENDPOINT_MULTIADDR = "multiaddr"
        const val PARAM_ACTIVE = "active"

        // I should't expose the public key but it is to didactic use
        const val XPUB = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"
    }
}