package com.robertogds.txviewer.util

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Roberto.Gil on 03/03/2018
 */

class DateUtil {
    companion object {
        fun getDate(milliSeconds: Long): String {
            val unixSeconds: Long = milliSeconds
            // convert seconds to milliseconds
            val date = Date(unixSeconds * 1000L)
            // the format of your date
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formattedDate = sdf.format(date)
            return formattedDate
        }
    }

}