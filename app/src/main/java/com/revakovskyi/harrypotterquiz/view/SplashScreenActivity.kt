package com.revakovskyi.harrypotterquiz.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revakovskyi.harrypotterquiz.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val duration = SPLASH_SCREEN_DURATION

    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Main).launch {
            binding.splashProgressBar.max = SPLASH_SCREEN_PROGRESS_MAX
            val value = SPLASH_SCREEN_PROGRESS_STOP

            ObjectAnimator
                .ofInt(binding.splashProgressBar, "progress", value)
                .setDuration(duration)
                .start()
            delay(duration)

            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val SPLASH_SCREEN_DURATION: Long = 2000
        private const val SPLASH_SCREEN_PROGRESS_MAX: Int = 1000
        private const val SPLASH_SCREEN_PROGRESS_STOP: Int = 990
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
