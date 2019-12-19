package com.gentalhacode.alfred

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * .:.:.:. Created by @thgMatajs on 19/12/19 .:.:.:.
 */
class AlfredApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}