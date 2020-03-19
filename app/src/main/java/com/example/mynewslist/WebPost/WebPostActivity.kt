package com.example.mynewslist

import android.os.Bundle
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class WebPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_post)

        // 웹뷰 시작
        // 웹뷰 시작
        val post_web_view = findViewById(R.id.post_web_view) as WebView
        post_web_view.setWebViewClient(WebViewClient()) // 클릭시 새창 안뜨게
        val mWebSettings = post_web_view.getSettings() //세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true) // 웹페이지 자바스클비트 허용 여부
        mWebSettings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(false) // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings.setLoadWithOverviewMode(true) // 메타태그 허용 여부
        mWebSettings.setUseWideViewPort(true) // 화면 사이즈 맞추기 허용 여부
        mWebSettings.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings.setBuiltInZoomControls(false) // 화면 확대 축소 허용 여부
        mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN) // 컨텐츠 사이즈 맞추기
        mWebSettings.setCacheMode(WebSettings.LOAD_NO_CACHE) // 브라우저 캐시 허용 여부
        mWebSettings.setDomStorageEnabled(true) // 로컬저장소 허용 여부

        var intent = intent
        post_web_view.loadUrl(intent.getStringExtra("news_url")) // 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작

    }
}
