package com.example.mynewslist.News

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mynewslist.R
import com.tuesda.walker.circlerefresh.CircleRefreshLayout.OnCircleRefreshListener
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
    var isLoading = false//로딩중인지?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        //setView()//뷰설정
        presenter.loadItems(arrayList, mAdapter)

    }

    //뷰 설정
    override fun setView(){
        refresh()
        Thread(Runnable {
            runOnUiThread {
                //리사이클러뷰 관련, 어댑터, 레이아웃매니저 설정
                news_recyclerview.adapter = mAdapter
                val lm = LinearLayoutManager(this)
                news_recyclerview.layoutManager = lm
                news_recyclerview.setHasFixedSize(true)
                //아이템 불러오기
                //스와이프 레이아웃 설정
                //swipe_layout.setOnRefreshListener { onRefresh() }
                swipe_layout.setOnRefreshListener(
                    object : OnCircleRefreshListener {
                        override fun refreshing() { // do something when refresh starts
                            onRefresh()
                            isLoading = true
                        }

                        override fun completeRefresh() { // do something when refresh complete
                            isLoading = false
                        }
                    })
            }
        }).start()




    }

    override fun refresh() {
        if(isLoading){
            isLoading = false
            swipe_layout.finishRefreshing()
        }else{
        }

    }

    //당겨서 새로고침
    override fun onRefresh() {
        arrayList.clear()
        var mAdapter2 = NewsAdapter(this, arrayList)//리사이클러뷰 어댑터
        presenter.loadItems(arrayList, mAdapter2)
    }

}
