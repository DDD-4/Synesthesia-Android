package com.ddd4.synesthesia.beer.presentation.ui.webview

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.databinding.ActivityWebviewBinding
import com.ddd4.synesthesia.beer.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_webview.*


class WebViewActivity : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview){

    companion object {
        const val WEBVIEW_URL = "webview_url"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initBind() {
        binding.apply {
            webview.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
                settings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
                settings.javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
                settings.loadWithOverviewMode = true // 메타태그 허용 여부
                settings.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
                settings.setSupportZoom(true) // 화면 줌 허용 여부
                settings.builtInZoomControls = false // 화면 확대 축소 허용 여부
                settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기
                settings.cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
                settings.domStorageEnabled = true // 로컬저장소 허용 여부
                intent?.getStringExtra(WEBVIEW_URL)?.let {
                    loadUrl(it)
                }
            }
        }
    }

    override fun onBackPressed() {
        when(binding.webview.canGoBack()) {
            true -> { webview.goBack() }
            false -> { finish() }
        }
    }
}