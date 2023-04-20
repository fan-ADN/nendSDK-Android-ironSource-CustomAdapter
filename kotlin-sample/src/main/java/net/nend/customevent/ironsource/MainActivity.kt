package net.nend.customevent.ironsource

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ironsource.mediationsdk.IronSource
import com.ironsource.mediationsdk.adunit.adapter.utility.AdInfo
import com.ironsource.mediationsdk.impressionData.ImpressionData
import com.ironsource.mediationsdk.impressionData.ImpressionDataListener
import com.ironsource.mediationsdk.logger.IronSourceError
import com.ironsource.mediationsdk.model.Placement
import com.ironsource.mediationsdk.sdk.LevelPlayInterstitialListener
import com.ironsource.mediationsdk.sdk.LevelPlayRewardedVideoListener
import net.nend.customevent.ironsource.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity :
    AppCompatActivity(),
    LevelPlayRewardedVideoListener,
    LevelPlayInterstitialListener,
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
        IronSource.setLevelPlayInterstitialListener(this)
        IronSource.setLevelPlayRewardedVideoListener(this)
        IronSource.setUserId(advertisingId)
        IronSource.addImpressionDataListener(this)
        IronSource.init(this, APP_KEY) {
            toast("Initializing IronSource SDK is succeeded")
        }
    }

    override fun onImpressionSuccess(impressionData: ImpressionData?) {
        impressionData?.let {
            toast("ImpressionSuccess: $it")
        }
    }

    //region LevelPlayListener common callbacks
    override fun onAdOpened(adInfo: AdInfo) {
        toast(capitalizeFirst(adInfo.adUnit) + "AdOpened")
    }

    override fun onAdShowSucceeded(adInfo: AdInfo) {
        toast(capitalizeFirst(adInfo.adUnit) + "AdShowSucceeded")
    }

    override fun onAdClosed(adInfo: AdInfo) {
        toast(capitalizeFirst(adInfo.adUnit) + "AdClosed")
    }

    override fun onAdShowFailed(ironSourceError: IronSourceError, adInfo: AdInfo) {
        toast(capitalizeFirst(adInfo.adUnit) + "AdShowFailed:" + ironSourceError)
    }
    //endregion

    //region LevelPlayInterstitialListener callbacks
    override fun onAdReady(adInfo: AdInfo) {
        toast("InterstitialAdReady")
    }

    override fun onAdClicked(adInfo: AdInfo) {
        toast("InterstitialAdClicked")
    }

    override fun onAdLoadFailed(ironSourceError: IronSourceError) {
        toast("InterstitialAdLoadFailed: $ironSourceError")
    }
    //endregion

    //region LevelPlayRewardedVideoListener callbacks
    override fun onAdAvailable(adInfo: AdInfo) {
        toast("RewardedVideoAvailable")
    }

    override fun onAdUnavailable() {
        toast("RewardedVideoUnavailable")
    }

    override fun onAdClicked(placement: Placement, adInfo: AdInfo) {
        toast("RewardedVideoAdClicked")
    }

    override fun onAdRewarded(placement: Placement, adInfo: AdInfo) {
        toast("RewardedVideoAdRewarded: $placement")
    }
    //endregion

    private fun capitalizeFirst(str: String): String {
        return if (str.isEmpty()) str
        else str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1)
    }
}
