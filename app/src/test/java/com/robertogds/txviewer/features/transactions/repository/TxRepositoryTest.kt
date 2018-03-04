package com.robertogds.txviewer.features.transactions.repository

import com.robertogds.txviewer.features.transactions.remote.ApiContract
import com.robertogds.txviewer.features.transactions.remote.ApiService
import com.robertogds.txviewer.features.transactions.remote.model.TxApiResponse
import com.robertogds.txviewer.features.transactions.remote.model.Wallet
import io.reactivex.Single
import io.reactivex.observers.TestObserver

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Roberto.Gil on 03/03/2018
 */
class TxRepositoryTest {
    @Mock
    private lateinit var mockApiService: ApiService

    @Mock
    private lateinit var mockApiResponse: TxApiResponse

    private lateinit var txRepository: TxRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        txRepository = TxRepository(mockApiService)
    }

    @Test
    fun getDataFromApi() {
        val xpub = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"
        Mockito.`when`(mockApiResponse.wallet).thenReturn(Wallet(10,2134L))
        Mockito.`when`(mockApiService.getJsonResponse(xpub)).thenReturn(Single.just(mockApiResponse))

        val testObserver = TestObserver<TxApiResponse>()

        txRepository.getDataFromApi(ApiContract.XPUB)
                .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { response -> response.wallet.finalBalance.equals(2134L) }
    }

}