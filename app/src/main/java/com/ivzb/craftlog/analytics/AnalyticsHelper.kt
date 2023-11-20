package com.ivzb.craftlog.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import java.util.Date

class AnalyticsHelper private constructor(context: Context) {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    fun logEvent(eventName: String, params: Bundle? = null) {
        firebaseAnalytics.logEvent(eventName, params)
    }

    companion object {

        @Volatile
        private var instance: AnalyticsHelper? = null

        fun getInstance(context: Context): AnalyticsHelper {
            return instance ?: synchronized(this) {
                instance ?: AnalyticsHelper(context).also { instance = it }
            }
        }
    }
}