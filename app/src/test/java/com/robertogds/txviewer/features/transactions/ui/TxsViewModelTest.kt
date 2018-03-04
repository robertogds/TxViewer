package com.robertogds.txviewer.features.transactions.ui

import com.robertogds.txviewer.features.transactions.remote.model.TxApiResponse
import com.robertogds.txviewer.features.transactions.remote.model.Wallet
import com.robertogds.txviewer.features.transactions.repository.TxRepository
import com.robertogds.txviewer.util.SchedulerProvider
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Roberto.Gil on 03/03/2018
 */
class TxsViewModelTest {
    @Mock
    private lateinit var mockRepository: TxRepository

    @Mock
    private lateinit var mockApiResponse: TxApiResponse

    private val schedulerProvider = SchedulerProvider(Schedulers.trampoline(), Schedulers.trampoline())

    private lateinit var txsViewModel: TxsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        txsViewModel = TxsViewModel(mockRepository, schedulerProvider)
    }

    @Test
    fun showDataFromApi() {
        val xpub = "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"

        Mockito.`when`(mockApiResponse.wallet).thenReturn(Wallet(10,10))

        Mockito.`when`(mockRepository.getDataFromApi(xpub)).thenReturn(Single.just(mockApiResponse))

        val testObserver = TestObserver<TxApiResponse>()

        txsViewModel.showDataFromApi()
                .subscribe(testObserver)

        testObserver.assertNoErrors()
        testObserver.assertValue { response -> response.wallet.numberTx == 10 }
    }
}