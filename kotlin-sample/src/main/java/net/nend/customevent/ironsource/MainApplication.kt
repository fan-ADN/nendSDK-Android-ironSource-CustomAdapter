package net.nend.customevent.ironsource

import android.content.Intent
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.google.android.gms.security.ProviderInstaller

class MainApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        // You need to update security provider if your app target Android 4.x
        // See also: https://developer.android.com/training/articles/security-gms-provider#patching
        ProviderInstaller.installIfNeededAsync(
            this,
            object : ProviderInstaller.ProviderInstallListener {
                override fun onProviderInstalled() {
                    Log.d(this.javaClass.simpleName, "onProviderInstalled")
                }

                override fun onProviderInstallFailed(errorCode: Int, intent: Intent?) {
                    Log.e(
                        this.javaClass.simpleName,
                        "onProviderInstallFailed, errorCode: $errorCode"
                    )
                }
            })
    }
}