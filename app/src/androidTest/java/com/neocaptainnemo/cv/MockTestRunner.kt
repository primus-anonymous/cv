package com.neocaptainnemo.cv

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.squareup.rx2.idler.Rx2Idler
import io.reactivex.plugins.RxJavaPlugins

class MockTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(cl, MockApp::class.java.name, context)
    }

    override fun onStart() {
        RxJavaPlugins.setInitIoSchedulerHandler(
                Rx2Idler.create("RxJava 2.x Io Scheduler"))

        super.onStart()
    }
}