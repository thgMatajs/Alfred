package com.gentalhacode.alfred.application

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.gentalhacode.data.di.dataModule
import com.gentalhacode.domain.di.domainModule
import com.gentalhacode.fire_base.di.firebaseModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

/**
 * .:.:.:. Created by @thgMatajs on 19/12/19 .:.:.:.
 */
class AlfredApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        setupKoinModules()
    }

    private fun setupKoinModules() {
        startKoin {
            androidContext(this@AlfredApplication)
            androidLogger()
            loadKoinModules(
                listOf(
                    domainModule,
                    dataModule,
                    firebaseModule
                )
            )
        }
    }
}