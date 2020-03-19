package com.example.mynewslist.News

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mynewslist.*
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity(),
    NewsContract.View, SwipeRefreshLayout.OnRefreshListener {
    private val TAG = "NewsActivity"
    internal var presenter: NewsPresenter = NewsPresenter().apply {//presenter정의해줌
        view = this@NewsActivity
        mContext = this@NewsActivity
    }
    var arrayList = arrayListOf<NewsModel>()//아이템에 들어갈 어레이리스트
    val mAdapter = NewsAdapter(this, arrayList)//리사이클러뷰 어댑터


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        setView()//뷰설정
    }

    //뷰 설정
    override fun setView(){
        //리사이클러뷰 관련, 어댑터, 레이아웃매니저 설정
        news_recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        news_recyclerview.layoutManager = lm
        news_recyclerview.setHasFixedSize(true)
        presenter.loadItems(arrayList, mAdapter)
        //스와이프 레이아웃 설정
        swipe_layout.setOnRefreshListener { onRefresh() }
    }

    //리사이클러뷰 새로고침
    override fun refresh() {
        if(swipe_layout.isRefreshing){
            swipe_layout.isRefreshing = false
        }

    }

    //당겨서 새로고침
    override fun onRefresh() {
        arrayList.clear()
        presenter.loadItems(arrayList, mAdapter)
    }
}
