package net.nend.customevent.ironsource

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ironsource.adapters.custom.nend.NendConfig
import com.ironsource.adapters.custom.nend.NendCustomInterstitial
import com.ironsource.adapters.custom.nend.NendCustomRewardedVideo
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.impressionData.ImpressionData
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.model.Placement
import com.ironsource.mediationsdk.sdk.InterstitialListener
import com.ironsource.mediationsdk.sdk.RewardedVideoListener
import net.nend.customevent.ironsource.databinding.ActivityMainBinding

class MainActivity :
    AppCompatActivity(),
    RewardedVideoListener,
    InterstitialListener,
    ImpressionDataListener {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val APP_KEY = "YOUR_APP_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            rvButton.setOnClickListener {
                if (IronSource.isRewardedVideoAvailable()) {
                    IronSource.showRewardedVideo()
                }
            }
            isLoadButton.setOnClickListener { IronSource.loadInterstitial() }
            isShowButton.setOnClickListener {
                if (IronSource.isInterstitialReady()) {
                    IronSource.showInterstitial()
                }
            }
        }
        setContentView(binding.root)

        val advertisingId = IronSource.getAdvertiserId(this@MainActivity)
        initIronSource(advertisingId)

        IronSource.shouldTrackNetworkState(this, true)
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initIronSource(advertisingId: String?) {
        IronSource.setAdaptersDebug(true)
        IronSource.setRewardedVideoListener(this)
        NendCustomRewardedVideo.nendAdUserFeature = NendConfig.UserFeature.Builder()
            .setAge(20)
            .setBirthday(2000, 1, 1)
            .setGender(NendConfig.Gender.MALE)
            .addCustomFeature("custom string value", "custom value")
            .addCustomFeature("custom double value", 1)
            .addCustomFeature("custom float value", 1.2)
            .addCustomFeature("custom boolean value", true)
            .build()
        IronSource.setInterstitialListener(this)
        NendCustomInterstitial.nendAdUserFeature = NendConfig.UserFeature.Builder()
            .setAge(20)
            .setBirthday(2000, 1, 1)
            .setGender(NendConfig.Gender.MALE)
            .addCustomFeature("custom string value", "custom value")
            .addCustomFeature("custom double value", 1)
            .addCustomFeature("custom float value", 1.2)
            .addCustomFeature("custom boolean value", true)
            .build()
        IronSource.setUserId(advertisingId)
        IronSource.addImpressionDataListener(this)
        IronSource.init(this, APP_KEY) {
            toast("Initializing IronSource SDK is succeeded")
        }
    }

    override fun onRewardedVideoAdOpened() {
        toast("onRewardedVideoAdOpened")
    }

    override fun onRewardedVideoAdClosed() {
        toast("onRewardedVideoAdClosed")
    }

    override fun onRewardedVideoAvailabilityChanged(state: Boolean) {
        toast("onRewardedVideoAvailabilityChanged: $state")
    }

    override fun onRewardedVideoAdStarted() {
        toast("onRewardedVideoAdStarted")
    }

    override fun onRewardedVideoAdEnded() {
        toast("onRewardedVideoAdEnded")
    }

    override fun onRewardedVideoAdRewarded(placement: Placement?) {
        toast("onRewardedVideoAdRewarded: $placement")
    }

    override fun onRewardedVideoAdShowFailed(error: IronSourceError?) {
        toast("onRewardedVideoAdShowFailed: $error")
    }

    override fun onRewardedVideoAdClicked(p0: Placement?) {
        toast("onRewardedVideoAdClicked")
    }

    override fun onInterstitialAdReady() {
        toast("onInterstitialAdReady")
    }

    override fun onInterstitialAdLoadFailed(error: IronSourceError?) {
        toast("onInterstitialAdLoadFailed: $error")
    }

    override fun onInterstitialAdOpened() {
        toast("onInterstitialAdOpened")
    }

    override fun onInterstitialAdClosed() {
        toast("onInterstitialAdClosed")
    }

    override fun onInterstitialAdShowSucceeded() {
        toast("onInterstitialAdShowSucceeded")
    }

    override fun onInterstitialAdShowFailed(error: IronSourceError?) {
        toast("onInterstitialAdShowFailed: $error")
    }

    override fun onInterstitialAdClicked() {
        toast("onInterstitialAdClicked")
    }

    override fun onImpressionSuccess(impressionData: ImpressionData?) {
        impressionData?.let {
            toast("onImpressionSuccess: $it")
        }
    }
}
