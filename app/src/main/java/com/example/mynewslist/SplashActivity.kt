package com.example.mynewslist

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.mynewslist.News.NewsActivity


class SplashActivity : AppCompatActivity() {
    val SPLASH_VIEW_TIME: Long = 1300 //2초간 스플래시 화면을 보여줌 (ms)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({ //delay를 위한 handler
            startActivity(Intent(this, NewsActivity::class.java))
            finish()
        }, SPLASH_VIEW_TIME)
    }
    override fun onBackPressed() { //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
