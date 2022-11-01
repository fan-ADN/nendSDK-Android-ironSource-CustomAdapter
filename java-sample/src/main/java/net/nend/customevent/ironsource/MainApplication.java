package net.nend.customevent.ironsource;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.security.ProviderInstaller;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // You need to update security provider if your app target Android 4.x
        // See also: https://developer.android.com/training/articles/security-gms-provider#patching
        ProviderInstaller.installIfNeededAsync(
                this,
                new ProviderInstaller.ProviderInstallListener() {
                    @Override
                    public void onProviderInstalled() {
                        Log.d(this.getClass().getSimpleName(), "onProviderInstalled");
                    }

                    @Override
                    public void onProviderInstallFailed(int errorCode, Intent intent) {
                        Log.e(this.getClass().getSimpleName(), "onProviderInstallFailed, errorCode: $errorCode");
                    }
                });
    }
}
