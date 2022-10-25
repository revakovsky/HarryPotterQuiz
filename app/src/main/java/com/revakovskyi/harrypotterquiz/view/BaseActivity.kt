package com.revakovskyi.harrypotterquiz.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.revakovskyi.harrypotterquiz.view.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding
    private lateinit var webView: WebView

    var filePath: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        webView = binding.webviewContainer

        setUpWebViewSettings(webView)

        if (savedInstanceState == null) {
            webView.loadUrl(getString(R.string.link))
        }
    }

    private fun setUpWebViewSettings(webView: WebView) {
        webView.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            webChromeClient = MyWebChromeClient(this@BaseActivity)
            settings.allowFileAccess = true
            settings.domStorageEnabled = true
            settings.databaseEnabled = true
            settings.setAppCacheEnabled(true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_CANCELED) {
            filePath?.onReceiveValue(null)
            return
        } else if (resultCode == Activity.RESULT_OK) {
            if (filePath == null) return

            filePath!!.onReceiveValue(
                WebChromeClient.FileChooserParams.parseResult(resultCode, data)
            )
            filePath = null
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else super.onBackPressed()

    }

    inner class MyWebChromeClient(val myActivity: Activity) : WebChromeClient() {
        override fun onShowFileChooser(
            webView: WebView?,
            filePathCallback: ValueCallback<Array<Uri>>?,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            filePath = filePathCallback

            val contentIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentIntent.type = "*/*"
            contentIntent.addCategory(Intent.CATEGORY_OPENABLE)

            startActivityForResult(contentIntent, 1)
            return true
        }
    }

}
