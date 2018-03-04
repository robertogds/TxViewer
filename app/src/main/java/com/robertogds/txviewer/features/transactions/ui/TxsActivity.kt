package com.robertogds.txviewer.features.transactions.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.robertogds.txviewer.R
import com.robertogds.txviewer.util.MonetaryUtil
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class TxsActivity : AppCompatActivity() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    @Inject lateinit var txsViewModel: TxsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        addDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
        compositeDisposable.dispose()
    }

    fun onRetryClick(view: View) {
        Log.d("TxsActivity", "Retry call: " + compositeDisposable.isDisposed)
        compositeDisposable.clear()
        layoutFailure.visibility = View.GONE
        layoutEmpty.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        addDisposable()
    }

    private fun addDisposable() {
        compositeDisposable.add(txsViewModel.showDataFromApi()
                .subscribeBy(onSuccess = {
                    Log.d("TxsActivity", it.wallet.finalBalance.toString())
                    progressBar.visibility = View.GONE
                    if (it.txs.isEmpty()) {
                        layoutEmpty.visibility = View.VISIBLE
                    } else {
                        recyclerView.adapter = TxAdapter(it.txs)
                        final_balance.text = MonetaryUtil(MonetaryUtil.UNIT_BTC).getDisplayAmountWithFormatting(it.wallet.finalBalance)
                    }

                }, onError = {
                    Log.d("TxsActivity", it.message)
                    progressBar.visibility = View.GONE
                    layoutFailure.visibility = View.VISIBLE
                }))
    }
}

