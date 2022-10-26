package com.revakovskyi.harrypotterquiz.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Debug
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.revakovskyi.harrypotterquiz.questions.model.EmptyRequest
import com.revakovskyi.harrypotterquiz.utils.ApiInterface
import com.revakovskyi.harrypotterquiz.view.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val duration = SPLASH_SCREEN_DURATION

    private lateinit var binding: ActivitySplashScreenBinding

    private var isADBEnabled: Boolean = false
    private var isDebuggable: Boolean = false
    private var isInternetConnected: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //checkResponse()

        if (isSettingsEnabled()) startMainActivity()
        else startBaseActivity()

    }

    private fun checkResponse() {
        val apiInterface = ApiInterface.create().getRequest()

        apiInterface.enqueue(object : Callback<EmptyRequest> {

            override fun onResponse(call: Call<EmptyRequest>, response: Response<EmptyRequest>) {
                Log.d("TAG", "onResponse success: ${response.body()}")

                if (isSettingsEnabled()) startMainActivity()
                else if (response.code() == 404) startMainActivity()
                else startBaseActivity()
            }

            override fun onFailure(call: Call<EmptyRequest>, t: Throwable) {
                Log.d("TAG", "error: ${t.message}")
                startMainActivity()
            }
        })
    }

    private fun isSettingsEnabled(): Boolean {
        isADBEnabled = Debug.isDebuggerConnected()
        isDebuggable =
            Settings.Secure.getInt(this.contentResolver, Settings.Secure.ADB_ENABLED, 0) == 1
        isInternetConnected = checkForInternet(this)

        return isADBEnabled || isDebuggable || !isInternetConnected
    }

    private fun startMainActivity() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.splashProgressBar.max = SPLASH_SCREEN_PROGRESS_MAX
            val value = SPLASH_SCREEN_PROGRESS_STOP

            ObjectAnimator
                .ofInt(binding.splashProgressBar, "progress", value)
                .setDuration(duration)
                .start()
            delay(duration)

            val intentToMainActivity =
                Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intentToMainActivity)
            finish()
        }
    }

    private fun startBaseActivity() {
        val intentToBaseActivity = Intent(this@SplashScreenActivity, BaseActivity::class.java)
        startActivity(intentToBaseActivity)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


    companion object {
        private const val SPLASH_SCREEN_DURATION: Long = 2000
        private const val SPLASH_SCREEN_PROGRESS_MAX: Int = 1000
        private const val SPLASH_SCREEN_PROGRESS_STOP: Int = 990
    }

}
