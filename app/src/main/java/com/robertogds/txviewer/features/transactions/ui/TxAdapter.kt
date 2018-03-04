package com.robertogds.txviewer.features.transactions.ui

/**
 * Created by Roberto.Gil on 03/03/2018
 */
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.robertogds.txviewer.R
import com.robertogds.txviewer.features.transactions.remote.model.Tx
import com.robertogds.txviewer.util.DateUtil
import com.robertogds.txviewer.util.MonetaryUtil
import com.robertogds.txviewer.util.MonetaryUtil.Companion.UNIT_BTC


/**
 * Provide views to RecyclerView with data from dataSet.
 *
 * Initialize the dataset of the Adapter.
 *
 * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
 */
class TxAdapter(private val dataSet: List<Tx>) :
        RecyclerView.Adapter<TxAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val timestamp: TextView = v.findViewById(R.id.tx_timestamp)
        val result: TextView = v.findViewById(R.id.tx_result)
        val status: TextView = v.findViewById(R.id.tx_status)

        init {
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener { Log.d(TAG, "Element $adapterPosition clicked.") }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.item_tx_row, viewGroup, false)

        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.itemView.context

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        val tx: Tx = dataSet[position]
        viewHolder.timestamp.text = DateUtil.getDate(tx.time)
        viewHolder.result.text = MonetaryUtil(UNIT_BTC).getDisplayAmountWithFormatting(tx.result)
        if (tx.result < 0) {
            viewHolder.result.setTextColor(ContextCompat.getColor(context,R.color.product_red_sent))
            viewHolder.status.text = context.getText(R.string.sent)
        } else {
            viewHolder.result.setTextColor(ContextCompat.getColor(context,R.color.product_green_received))
            viewHolder.status.text = context.getText(R.string.received)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    companion object {
        private val TAG = "TxAdapter"
    }
}