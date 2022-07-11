package net.nend.customevent.ironsource;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.impressionData.ImpressionData;
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

public class MainActivity extends AppCompatActivity implements RewardedVideoListener, InterstitialListener, ImpressionDataListener {

    private static final String APP_KEY = "YOUR_APP_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rvButton = findViewById(R.id.rv_button);
        rvButton.setOnClickListener(v -> {
            if (IronSource.isRewardedVideoAvailable()) {
                IronSource.showRewardedVideo();
            }
        });
        Button isLoadButton = findViewById(R.id.is_load_button);
        isLoadButton.setOnClickListener(v -> IronSource.loadInterstitial());
        Button isShowButton = findViewById(R.id.is_show_button);
        isShowButton.setOnClickListener(v -> {
            if (IronSource.isInterstitialReady()) {
                IronSource.showInterstitial();
            }
        });

        String advertisingId = IronSource.getAdvertiserId(this);
        initIronSource(advertisingId);
    }

    private void initIronSource(String advertisingId) {
        IronSource.setAdaptersDebug(true);
        IronSource.setRewardedVideoListener(this);
        IronSource.setInterstitialListener(this);
        IronSource.setUserId(advertisingId);
        IronSource.addImpressionDataListener(this);
        IronSource.init(this, APP_KEY, () -> toast("Initializing IronSource SDK is succeeded"));
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRewardedVideoAdOpened() {
        toast("onRewardedVideoAdOpened");
    }

    @Override
    public void onRewardedVideoAdClosed() {
        toast("onRewardedVideoAdClosed");
    }

    @Override
    public void onRewardedVideoAvailabilityChanged(boolean state) {
        toast("onRewardedVideoAvailabilityChanged: " + state);
    }

    @Override
    public void onRewardedVideoAdStarted() {
        toast("onRewardedVideoAdStarted");
    }

    @Override
    public void onRewardedVideoAdEnded() {
        toast("onRewardedVideoAdEnded");
    }

    @Override
    public void onRewardedVideoAdRewarded(Placement placement) {
        toast("onRewardedVideoAdRewarded: " + placement);
    }

    @Override
    public void onRewardedVideoAdShowFailed(IronSourceError ironSourceError) {
        toast("onRewardedVideoAdShowFailed: " + ironSourceError);
    }

    @Override
    public void onRewardedVideoAdClicked(Placement placement) {
        toast("onRewardedVideoAdClicked");
    }

    @Override
    public void onInterstitialAdReady() {
        toast("onInterstitialAdReady");
    }

    @Override
    public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
        toast("onInterstitialAdLoadFailed: " + ironSourceError);
    }

    @Override
    public void onInterstitialAdOpened() {
        toast("onInterstitialAdOpened");
    }

    @Override
    public void onInterstitialAdClosed() {
        toast("onInterstitialAdClosed");
    }

    @Override
    public void onInterstitialAdShowSucceeded() {
        toast("onInterstitialAdShowSucceeded");
    }

    @Override
    public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
        toast("onInterstitialAdShowFailed: " + ironSourceError);
    }

    @Override
    public void onInterstitialAdClicked() {
        toast("onInterstitialAdClicked");
    }


    @Override
    public void onImpressionSuccess(ImpressionData impressionData) {
        if (impressionData != null) {
            toast("onImpressionSuccess: " + impressionData);
        }
    }
}