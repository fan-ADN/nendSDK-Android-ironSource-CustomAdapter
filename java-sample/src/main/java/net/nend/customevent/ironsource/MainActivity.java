package net.nend.customevent.ironsource;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo;
import com.ironsource.mediationsdk.impressionData.ImpressionData;
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener;
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener;

public class MainActivity extends AppCompatActivity implements LevelPlayRewardedVideoListener, LevelPlayInterstitialListener, ImpressionDataListener {

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
        IronSource.setLevelPlayInterstitialListener(this);
        IronSource.setLevelPlayRewardedVideoListener(this);
        IronSource.setUserId(advertisingId);
        IronSource.addImpressionDataListener(this);
        IronSource.init(this, APP_KEY, () -> toast("Initializing IronSource SDK is succeeded"));
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onImpressionSuccess(ImpressionData impressionData) {
        if (impressionData != null) {
            toast("ImpressionSuccess: " + impressionData);
        }
    }

    //region LevelPlayListener common callbacks
    @Override
    public void onAdOpened(AdInfo adInfo) {
        toast(capitalizeFirst(adInfo.getAdUnit()) + "AdOpened");
    }

    @Override
    public void onAdShowSucceeded(AdInfo adInfo) {
        toast(capitalizeFirst(adInfo.getAdUnit()) + "AdShowSucceeded");
    }

    @Override
    public void onAdClosed(AdInfo adInfo) {
        toast(capitalizeFirst(adInfo.getAdUnit()) + "AdClosed");
    }

    @Override
    public void onAdShowFailed(IronSourceError ironSourceError, AdInfo adInfo) {
        toast(capitalizeFirst(adInfo.getAdUnit()) + "AdShowFailed:" + ironSourceError);
    }
    //endregion

    //region LevelPlayInterstitialListener callbacks
    @Override
    public void onAdReady(AdInfo adInfo) {
        toast("InterstitialAdReady");
    }

    @Override
    public void onAdClicked(AdInfo adInfo) {
        toast("InterstitialAdClicked");
    }

    @Override
    public void onAdLoadFailed(IronSourceError ironSourceError) {
        toast("InterstitialAdLoadFailed: " + ironSourceError);
    }
    //endregion

    //region LevelPlayRewardedVideoListener callbacks
    @Override
    public void onAdAvailable(AdInfo adInfo) {
        toast("RewardedVideoAvailable");
    }

    @Override
    public void onAdUnavailable() {
        toast("RewardedVideoUnavailable");
    }

    @Override
    public void onAdClicked(Placement placement, AdInfo adInfo) {
        toast("RewardedVideoAdClicked");
    }

    @Override
    public void onAdRewarded(Placement placement, AdInfo adInfo) {
        toast("RewardedVideoAdRewarded: " + placement);
    }
    //endregion

    private String capitalizeFirst(String str) {
        if (str.length() == 0) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}