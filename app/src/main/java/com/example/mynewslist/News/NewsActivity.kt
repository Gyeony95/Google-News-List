package com.example.mynewslist.News

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mynewslist.R
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity(),
    NewsContract.View, SwipeRefreshLayout.OnRefreshListener {
    private val TAG = "NewsActivity"
    internal var presenter: NewsPresenter = NewsPresenter().apply {//presenter정의해줌
        view = this@NewsActivity
        mContext = this@NewsActivity
    }
    var arrayList = arrayListOf<NewsModel>()//아이템에 들어갈 어레이리스트
    var mAdapter = NewsAdapter(this, arrayList)//리사이클러뷰 어댑터
    var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        presenter.loadItems(arrayList,mAdapter)
        setView()
    }
    //뷰 설정
    override fun setView(){
        //리사이클러뷰 관련, 어댑터, 레이아웃매니저 설정
        if(isFirst){
            Thread(Runnable {
                runOnUiThread {
                    val lm = LinearLayoutManager(this)
                    news_recyclerview.layoutManager = lm
                    news_recyclerview.adapter = mAdapter
                    news_recyclerview.setHasFixedSize(true)
                    //아이템 불러오기
                    //스와이프 레이아웃 설정
                    swipe_layout.setOnRefreshListener {
                        try{ onRefresh() }catch (e:Exception){} }
                }
            }).start()
        }
        refresh()
    }
    override fun refresh() {
        if(swipe_layout.isRefreshing){
            swipe_layout.isRefreshing = false
        }
    }
    //당겨서 새로고침
    override fun onRefresh() {
        try{
            swipe_layout.isRefreshing = true
            arrayList.clear()
            mAdapter = NewsAdapter(this, arrayList)//리사이클러뷰 어댑터
            presenter.loadItems(arrayList,mAdapter)
        }catch (e:Exception){
            Log.e(TAG,e.message)
        }
    }
}
