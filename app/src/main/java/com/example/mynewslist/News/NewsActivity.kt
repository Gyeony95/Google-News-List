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
    internal var presenter: NewsPresenter = NewsPresenter().apply {
        view = this@NewsActivity
        mContext = this@NewsActivity
    }
    var arrayList = arrayListOf<NewsModel>()
    val mAdapter = NewsAdapter(this, arrayList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        //리사이클러뷰 관련, 어댑터, 레이아웃매니저
        news_recyclerview.adapter = mAdapter
        val lm = LinearLayoutManager(this)
        news_recyclerview.layoutManager = lm
        news_recyclerview.setHasFixedSize(true)//아이템이 추가삭제될때 크기측면에서 오류 안나게 해줌

        presenter.loadItems(arrayList, mAdapter)

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
